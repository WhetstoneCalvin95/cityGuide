package edu.txstate.sl20.cityguide;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class AttractionInfoActivity extends AppCompatActivity {
    int intId;
    double dblCost;
    String strUrl;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_info);

        SharedPreferences sharedPref2 = PreferenceManager.getDefaultSharedPreferences(this);
        intId = sharedPref2.getInt("id", 0);
        dblCost = sharedPref2.getFloat("cost", 0);
        strUrl = sharedPref2.getString("url", "");
        position = sharedPref2.getInt("pos", 0);

        Button costCalculation = findViewById(R.id.btnCalculateCost);
        final Button goToWebsite = findViewById(R.id.btnGoToWebsite);
        Button addAttraction = findViewById(R.id.btnAddAttraction);


        if (strUrl.equals("")) {
            goToWebsite.setEnabled(false);
        }

        goToWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goToWebsite.isEnabled()) {
                    //Start an activity
                }

            }
        });


        final EditText numberOfPersons = findViewById(R.id.txtNumberOfPersons);

        costCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intPersons = Integer.parseInt(numberOfPersons.getText().toString());
                double dblTotalCost = dblCost * intPersons;
                DecimalFormat currency = new DecimalFormat("$###,###.##");
                Toast.makeText(AttractionInfoActivity.this, "Total Cost:" +
                        currency.format(dblTotalCost) + ".", Toast.LENGTH_LONG).show();

            }
        });


        addAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_add);
            }
        });


        Button update = findViewById(R.id.btnUpdate);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "attractions/" + position + "/Cost.json";
                //position is the row of table you want to update.
                //location is an attribute, attribute of notes, like a column.
                StringEntity entity = null;

                try {
                    double newCost = dblCost + 1;
                    entity = new StringEntity("" + newCost); //This is for a number without \".
                    // entity = new StringEntity("\"for a string\"");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
                RestAPIClient.put(AttractionInfoActivity.this, url, entity,
                        "application/text", new TextHttpResponseHandler() {


                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Toast.makeText(AttractionInfoActivity.this, "success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(AttractionInfoActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });




    }
}