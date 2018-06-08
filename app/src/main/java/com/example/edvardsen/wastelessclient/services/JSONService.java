package com.example.edvardsen.wastelessclient.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Epico-u-01 on 4/6/2018.
 */

public class JSONService {

    public static JSONArray toJSONArray(InputStream inputStream){
        JSONArray jsonArray = null;
        try{
            //Read JSON
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonArray = new JSONArray(responseStrBuilder.toString());
            return jsonArray;
        }catch (Exception e){
            //TODO: Log Exception
            return jsonArray;
        }
    }

    public static JSONObject toJSONObject(InputStream inputStream){
        JSONObject jsonObject = null;
        try{
            //Read JSON
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonObject = new JSONObject(responseStrBuilder.toString());
            return jsonObject;
        }catch (Exception e){
            //TODO: Log Exception
            return jsonObject;
        }
    }
}
