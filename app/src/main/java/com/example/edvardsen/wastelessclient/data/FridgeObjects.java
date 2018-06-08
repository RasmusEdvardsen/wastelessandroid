package com.example.edvardsen.wastelessclient.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.activities.BarcodeActivity;
import com.example.edvardsen.wastelessclient.activities.MainActivity;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.HandlerService;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FridgeObjects extends Activity {


    RelativeLayout relativeLayoutProgressBar;
//Store ExpDate
    public Date expDate;
//Store Name
    public String name;
    //Store EAN
    public String ean;


    public FridgeObjects( String name, Date expDate, String ean){

        this.expDate = expDate;
        this.name = name;
        this.ean= ean;

    }

    public void SendFridgeToDb(final Context ctx){

        Log.i("information toDb", "userid: " + String.valueOf(UserModel.getUserID()) + " expdata: " + expDate.toString() + " name: " + name + " ean: " + ean);
        final String url = Constants.baseURL + Constants.productsPath;
        final String json = "{\"UserID\": " + "\"" + String.valueOf(UserModel.getUserID()) + "\","
                + "\"EAN\": " + "\"" + ean + "\","
                + "\"FoodTypeName\": " + "\"" + name + "\","
                + "\"ExpirationDate\": " + "\"" + DateFormat.format("yyyy-MM-dd", expDate) + "\"," + "}";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    response = client.newCall(request).execute();

                    if(response == null){
                        HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                    }else{
                        if(response.code() == 200){

                            Log.i("information", "Response is: " + String.valueOf(response.code()));

                            HandlerService.makeToast(ctx, "Product added!", Toast.LENGTH_SHORT, 500);
                            ctx.startActivity(new Intent(ctx, BarcodeActivity.class));
                        }else{
                            Log.i("information", String.valueOf(response.code()));
                        }
                    }

                }catch (Exception e){
                    Log.e("information", e.toString());
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate){
        this.expDate=expDate;
    }
}
