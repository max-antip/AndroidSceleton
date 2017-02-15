package http;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import event.EventBus;
import model.Request;
import model.Response;

public class HttpClient {

    private static final String KEY = "19fbb1b5-327b-470c-816d-f725dd6adf8b";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_TYPE = "Bearer";
    public static final String DATE_FORMATE_VIAPHONE = "yyyy-MM-dd";
    private Gson gson;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ThreadPoolExecutor thredExecutore;
    private static final int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    private final BlockingQueue<Runnable> threadQueue;

    public HttpClient() {
        gson = new GsonBuilder().setDateFormat(DATE_FORMATE_VIAPHONE).create();
        threadQueue = new LinkedBlockingQueue<Runnable>();
        thredExecutore = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                threadQueue);
    }


    public void asynchSend(final Request req) {
        thredExecutore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EventBus.fireEvent(processJsonRequest(req));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private synchronized Response processJsonRequest(Request request) throws IOException, JSONException {

        HttpURLConnection connection = makeConnection(request);
        OutputStreamWriter printout;

        printout = new OutputStreamWriter(connection.getOutputStream());
        printout.write(request.toJsonString());
        printout.flush();
        printout.close();

        int status = connection.getResponseCode();
        StringBuilder sbResp = new StringBuilder();
        BufferedReader br = null;
        switch (status) {
            case 200:
            case 201:
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sbResp.append(line).append("\n");
                }
                break;
            default:
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String err;
                while ((err = br.readLine()) != null) {
                    sbResp.append(err).append("\n");
                }
                System.out.println(sbResp);
                break;
        }
        br.close();

        JsonReader reader = new JsonReader(new StringReader((sbResp.toString())));
        reader.setLenient(true);
        return gson.fromJson(reader, request.getResp());
    }

    @NonNull
    private HttpURLConnection makeConnection(Request request) throws IOException {
        URL url = new URL(request.getUrl());
        OutputStreamWriter printout;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty(AUTHORIZATION, AUTH_TYPE + " " + KEY);
        connection.connect();
        return connection;
    }

}
