package viaphone.com.viasceleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import event.EventBus;
import event.EventBus.RespListener;
import http.HttpClient;
import model.Offer;
import model.OffersRequest;
import model.OffersResponse;
import model.Response;

public class MainActivity extends AppCompatActivity implements RespListener {

    static {
        System.loadLibrary("native-lib");
    }

    private HttpClient client;
    private LinearLayout infoField;
    private TextView respInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.addListener(this);
        setContentView(R.layout.activity_main);
        client = new HttpClient();
        infoField = (LinearLayout) findViewById(R.id.info_field);
        respInfo = (TextView) findViewById(R.id.response_info);
        Button offerReqBut = (Button) findViewById(R.id.make_offer_req);
        assert offerReqBut != null;
        offerReqBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.asynchSend(new OffersRequest());
            }
        });
    }


    @Override
    public void onEvent(Response response) {
        respInfo.setText(response.getClass().getSimpleName()+" :");
        if (response instanceof OffersResponse) {
            final OffersResponse resp = (OffersResponse) response;
            if (!resp.getOffers().isEmpty()) {
                infoField.removeAllViews();
                for (Offer off : resp.getOffers()) {
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText(off.getProductName());
                    infoField.addView(tv);
                }
            } else {
                TextView tv = new TextView(MainActivity.this);
                tv.setText(getString(R.string.mes_no_offers));
            }

        }

    }


    public native String stringFromJNI();
}
