package com.nutra_o.nutra_o.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nutra_o.nutra_o.models.Constants;
import com.nutra_o.nutra_o.models.FoodInfo;
import com.nutra_o.nutra_o.models.ShoppingList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ars on 06-04-2015.
 */
public class FoodInfoAccesor {

    ServiceConnector serviceConnector;

    public FoodInfoAccesor(ServiceConnector serviceConnector){
        this.serviceConnector = serviceConnector;
    }


    public ArrayList<FoodInfo> seachFoodByName(String name){

        String response;

        try {

            response = serviceConnector.get(Constants.getSeachFoodInfoByNameURL(name));

            Gson gson = new GsonBuilder().create();
            ArrayList<FoodInfo> list = gson.fromJson(response, new TypeToken<ArrayList<FoodInfo>>() {}.getType());

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
