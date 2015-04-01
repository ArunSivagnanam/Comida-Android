package com.nutra_o.nutra_o.service;

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

    public User authenticate(String email, String password){

      String response;
      User user = null;
        try {

            response = serviceConnector.get(Constants.getAuthenticationURL()+"?email="+email+"&password="+password);
            JSONObject json = new JSONObject(response);
            user  = new User(json.getInt("id"),json.getString("firstName"),
                    json.getString("lastName"),json.getString("address"),
                    json.getString("email"),json.getString("userName"),json.getString("passWord"),
                    json.getBoolean("active"));
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
