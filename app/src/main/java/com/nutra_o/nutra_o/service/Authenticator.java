package com.nutra_o.nutra_o.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nutra_o.nutra_o.models.Constants;
import com.nutra_o.nutra_o.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ars on 19-03-2015.
 */
public class Authenticator {

    ServiceConnector serviceConnector;

    public Authenticator(ServiceConnector serviceConnector){
        this.serviceConnector = serviceConnector;
    }

    /*TEMPERARY FIX
    *  must be changed to post to pass json with email and password */

    public User authenticate(String email, String password) {

      String response;
      User user = null;
      JSONObject jsonPost = new JSONObject();

        try {
            jsonPost.put("email",email);
            jsonPost.put("password",password);
            response = serviceConnector.post(Constants.getAuthenticationURL(),jsonPost.toString());

            Gson gson = new GsonBuilder().create();
            user = gson.fromJson(response, User.class);

            JSONObject json = new JSONObject(response);
            user  = new User(json.getInt("ID"),json.getString("FirstName"),
                    json.getString("LastName"),json.getString("Address"),
                    json.getString("Email"),json.getString("Username"),json.getString("Password"),
                    json.getBoolean("Active"),json.getInt("SubscriptionType"));
            if(user != null){
                return user;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
