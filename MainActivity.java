package edu.txstate.sl20.cityguide;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class MainActivity extends ListActivity {

    List<Attraction> attractions ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);
            getAttractions();


        }
        void getAttractions(){
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("Accept", "application/json"));
            RestAPIClient.get(MainActivity.this, "attractions.json", headers.toArray(new Header[headers.size()]),
                    null, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            //Make an array of Attraction objects
                            attractions = new ArrayList<Attraction>();
                            for (int i=0; i <response.length(); i++){
                                try {
                                    attractions.add(new Attraction(response.getJSONObject(i)));
                                } catch (Exception ex ) {ex.printStackTrace();}
                            }
                            setListAdapter(new ArrayAdapter<Attraction>(MainActivity.this, R.layout.activity_main,
                                                   R.id.txtTravel , attractions));
                        }
                    });

        }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {//position = index
        //super.onListItemClick(l, v, position, id);
        if (position == 0) {
            //Toast.makeText(MainActivity.this, "You select Art Institute.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://artic.edu")));
        } else {


            Attraction selectedAttraction =  attractions.get(position);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("id", selectedAttraction.getId());
            editor.putFloat("cost", (float) selectedAttraction.getCost());
            editor.putString("url", selectedAttraction.getUrl());
            editor.putInt("pos", position);

            editor.commit();


            startActivity(new Intent(MainActivity.this, AttractionInfoActivity.class));


            DecimalFormat tenth = new DecimalFormat("$###,###.##");
            Toast.makeText(MainActivity.this, "You select " + selectedAttraction.getName() +
                    ", the cost is " + tenth.format(selectedAttraction.getCost())+".", Toast.LENGTH_LONG).show();
        }

        //look up selected element in the array
        //String strSelectedAttraction= strAttractions[position];
        //Toast.makeText(MainActivity.this, "You select " + strSelectedAttraction + ".", Toast.LENGTH_LONG).show();



    }
}
