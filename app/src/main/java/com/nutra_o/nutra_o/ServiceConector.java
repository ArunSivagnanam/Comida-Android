package com.nutra_o.nutra_o;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ServiceConector {

    StringBuilder stringBuilder;
    HttpClient httpClient;


    public ServiceConector(){

        stringBuilder = new StringBuilder();
        httpClient = new DefaultHttpClient();

    }

    public String get(String url)  {
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept","application/json");
        httpGet.addHeader("Content-Type","application/json");

        String line = null;
        try{

            HttpResponse response = httpClient.execute(httpGet,localContext);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
            } else {
                Log.i("JSON", "Failed to download file");
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return line;
    }

    public String post(String url){
        HttpPost httpPost = new HttpPost(url);
        return null;
    }

    public String put(String url){
        HttpPut httpGet = new HttpPut(url);
        return null;
    }

    public void delete(String url){
        HttpDelete httpGe = new HttpDelete(url);

    }



}
