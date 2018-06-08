package com.example.edvardsen.wastelessclient.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderService {
    public static String ResulttoString(InputStream inputStream){
        JSONArray jsonArray = null;
        String result = "";

        try{
            //Read JSON
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            result = responseStrBuilder.toString();
            return result;
        }catch (Exception e){
            //TODO: Log Exception
            return result;
        }
    }


}
