package com.nutra_o.nutra_o.service;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nutra_o.nutra_o.models.Constants;
import com.nutra_o.nutra_o.models.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Created by ars on 02-04-2015.
 */
public class ShoppingListAccesor {

    ServiceConnector serviceConnector;

    public ShoppingListAccesor(ServiceConnector serviceConnector){
        this.serviceConnector = serviceConnector;
    }

    public ArrayList<ShoppingList> getAllShoppingList(int userID){

        String response;

        try {

            response = serviceConnector.get(Constants.getAllShoppingListsURL(userID));

            Gson gson = new GsonBuilder().create();
            ArrayList<ShoppingList> list = gson.fromJson(response, new TypeToken<ArrayList<ShoppingList>>() {}.getType());

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean postNewShoppingList(ShoppingList l){

        String response;
        Gson gson = new Gson();
        String jsonBody = gson.toJson(l);

        try {

            response = serviceConnector.post(Constants.addShoppingListUrl(),jsonBody);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (response != null){
            return true;
        }else{
            return false;
        }
    }




}
