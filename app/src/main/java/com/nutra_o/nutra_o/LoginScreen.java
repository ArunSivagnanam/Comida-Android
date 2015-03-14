package com.nutra_o.nutra_o;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class LoginScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        view = (TextView) findViewById(R.id.textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void testMethod(View v){
        Log.i("INFO","button method called");

        new AsyncTask<String, String, String>() {

            protected String doInBackground(String... urls) {

                ServiceConnector serviceConnector = new ServiceConnector();


                String json = "{\n" +
                        "\"id\": \"1\",\n" +
                        "\"firstName\" :\"Arukjkjkjn\",\n" +
                        "\"lastName\" : \"Sivagnanam\",\n" +
                        "\"address\" : \"Haraldslundvej\",\n" +
                        "\"email\" : \"Arun.s@live.dk\",\n" +
                        "\"userName\" : \"Arun.s\",\n" +
                        "\"passWord\" : \"1234\",\n" +
                        "\"active\" : \"true\"\n" +
                        "}";


                String getResult = null;
                try {
                    getResult = serviceConnector.post("http://comida-service.azurewebsites.net/api/user",json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("result is = "+getResult);


                // String postResult = serviceConnector.sendPost("http://comida-service.azurewebsites.net/api/user",json);

                //System.out.println(postResult);

                try {
                    JSONArray array = new JSONArray(getResult);
                    System.out.println(array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return getResult;
            }

            protected void onProgressUpdate(String... progress) {

            }

            protected void onPostExecute(String result) {
                view.setText(result);
            }
        }.execute();
    }

    TextView view;


}
