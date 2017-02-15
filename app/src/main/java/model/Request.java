package model;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {

    String url;
    protected long ref;
    Class<? extends Response> resp;

    private JSONObject toJson() throws JSONException {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("resp") || f.getName().equals("url");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        return new JSONObject(gson.toJson(this));
    }

    public String toJsonString() throws JSONException {
        return toJson().toString();
    }

    public String getUrl() {
        return url;
    }

    public Class<? extends Response> getResp() {
        return resp;
    }
}
