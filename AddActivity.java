package edu.txstate.sl20.cityguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class AddActivity extends AppCompatActivity {

    int intId;
    double dblCost;
    String strName;
    String strURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        final EditText id = findViewById(R.id.txtID);
        final EditText name = findViewById(R.id.txtName);
        final EditText cost = findViewById(R.id.txtCost);
        final EditText url = findViewById(R.id.txtURL);
        Button newAttraction = findViewById(R.id.btnNewAttraction);




        newAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intId = Integer.parseInt(id.getText().toString());
                dblCost = Double.parseDouble(cost.getText().toString());
                strName = name.getText().toString();
                strURL = url.getText().toString();




                String url = "attractions.json";
                JSONObject att = new JSONObject();
                try{
                    att.put("Id", intId);
                    att.put("Name", strName);
                    att.put("Url", strURL);
                    att.put("Cost", dblCost);
                } catch (Exception ex) {ex.printStackTrace();}


                StringEntity entity = null;

                try {
                    entity = new StringEntity(att.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                RestAPIClient.post(AddActivity.this, url, entity,
                        "application/text", new TextHttpResponseHandler() {


                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Toast.makeText(AddActivity.this, "success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(AddActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
    }
}
