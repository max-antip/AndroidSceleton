package event;


import android.os.Handler;

import java.util.concurrent.CopyOnWriteArrayList;

import model.Response;

public class EventBus {

    private static final EventBus instance = new EventBus();
    private CopyOnWriteArrayList<RespListener> listeners = new CopyOnWriteArrayList<>();
    private Handler handler;

    private EventBus() {
        handler = new Handler();
    }

    public static void fireEvent(final Response response) {
        instance.handler.post(new Runnable() {
            @Override
            public void run() {
                for (RespListener re : instance.listeners) {
                    re.onEvent(response);
                }
            }
        });
    }

    public static void addListener(RespListener resp) {
        instance.listeners.add(resp);
    }

    public static void removeListener(RespListener resp) {
        instance.listeners.remove(resp);
    }

    public interface RespListener {

        void onEvent(Response resp);

    }
}

