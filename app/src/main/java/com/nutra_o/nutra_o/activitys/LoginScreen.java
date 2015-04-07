package com.nutra_o.nutra_o.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.Constants;
import com.nutra_o.nutra_o.models.User;

import java.util.Observable;
import java.util.Observer;


public class LoginScreen extends ActionBarActivity implements Observer {

    boolean loginPressed;
    ApplicationImpl application;
    ApplicationModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        application = (ApplicationImpl) getApplicationContext();
        model = application.getModel();
        model.addObserver(this);

        initViewComponents();
        loginPressed = false;

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


    public void login(View v){
        loginView.setVisibility(View.VISIBLE);
        loginButton.setText("ENTER");

        // USE ASYNC TASK

        if(loginPressed){
            // read data and login (Runnable) ()->

            final String email = ((EditText)findViewById(R.id.username_editText)).getText().toString();
            final String password = ((EditText)findViewById(R.id.password_editText)).getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    User currentUser =  application.authenticator.authenticate(email,password);
                    model.setUser(currentUser);
                }
            }).start();

        }else{
            loginPressed = true;
        }
    }

    public void observerTest(View v){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(500);
                    model.setText("hello");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void clearLoginView(View v){
        loginView.setVisibility(View.GONE);
        loginButton.setText("LOGIN");
        loginPressed = false;
    }

    public void register(View v){

    }

    public void initViewComponents(){
        loginView = (LinearLayout)findViewById(R.id.loginView);
        loginButton = (Button) findViewById(R.id.button_login);
        webview =  (WebView)findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://onceaunicorn.com/");
        webview.setWebViewClient(new WebViewClient());

    }

    LinearLayout loginView;
    WebView webview;
    Button loginButton;

    @Override
    public void update(final Observable observable, final Object data) {

        Constants.Event event =  (Constants.Event) data;

        if( event == Constants.Event.USER_LOADED){

            if(model.currentUser != null){
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "Wrong email or password \nUse test user Arun.s@live.dk, 1234",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.deleteObserver(this);
    }
}
