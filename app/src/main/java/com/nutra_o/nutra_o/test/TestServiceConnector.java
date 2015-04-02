package com.nutra_o.nutra_o.test;

import com.nutra_o.nutra_o.service.ServiceConnector;

import java.io.IOException;

/**
 * Created by ars on 19-03-2015.
 */
public class TestServiceConnector {

    public void test(){

        ServiceConnector serviceConnector = new ServiceConnector();


        String json = "{\n" +
                "\"ID\": \"1\",\n" +
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
    }


}
