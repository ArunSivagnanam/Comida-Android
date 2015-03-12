package com.nutra_o.nutra_o;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

        new Thread(new Runnable(){


            @Override
            public void run() {
                try {
                    InputStream stream = new URL("http://comida-service.azurewebsites.net/api/user").openStream();
                    InputStreamReader is = new InputStreamReader(stream);

                    BufferedReader reader = new BufferedReader(is);

                    String line;
                    StringBuilder str =  new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        str.append(line);
                    }
                    reader.close();

                    Log.i("JSON RESULT",line);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
}
