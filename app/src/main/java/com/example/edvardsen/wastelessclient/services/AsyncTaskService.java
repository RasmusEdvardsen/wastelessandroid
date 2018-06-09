package com.example.edvardsen.wastelessclient.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.activities.BarcodeActivity;
import com.example.edvardsen.wastelessclient.activities.MainActivity;
import com.example.edvardsen.wastelessclient.data.Product;
import com.example.edvardsen.wastelessclient.data.UserModel;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncTaskService {
    public void LoginFlow(final Context ctx, final RelativeLayout relativeLayoutProgressBar, final String emailInput, final String passwordInput){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HandlerService.setVisibility(relativeLayoutProgressBar, View.VISIBLE);
                try{
                    // Create URL
                    URL loginURL = new URL(Constants.baseURL + Constants.usersPath + "/?email=" + emailInput + "&password=" + passwordInput);

                    // Create connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                    if(httpURLConnection.getResponseCode() == 200){
                        JSONObject jsonObject = ReaderService.toJSONObject(httpURLConnection.getInputStream());
                        UserModel.getInstance();
                        UserModel.setEmail(jsonObject.getString("Email"));
                        UserModel.setPassword(jsonObject.getString("Password"));
                        UserModel.setUserID((Integer.parseInt(jsonObject.getString("UserID"))));
                        JSONArray jsonArray = jsonObject.getJSONArray("ProductsConcrete");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            Product product = new Product(jObject.getString("Name")
                                    , jObject.getString("ExpiryDate")
                                    , jObject.getString("Id"));
                            UserModel.addProduct(product);
                        }
                        Log.i("information", String.valueOf(UserModel.getProducts().size()));
                        Log.i("information", jsonObject.toString());
                        ctx.startActivity(new Intent(ctx, MainActivity.class));
                    }
                }catch (Exception e){
                    Log.e("information", e.toString());
                    HandlerService.makeToast(ctx, "Something went wrong.", Toast.LENGTH_SHORT, 500);
                }
                HandlerService.setVisibility(relativeLayoutProgressBar, View.GONE, 500);
            }
        });
    }

    public void SignUpFlow (final Context ctx, final RelativeLayout relativeLayoutProgressBar, final String emailInput, final String passwordInput){
        final String json = "{\"email\": " + "\"" + emailInput + "\","
                + "\"password\": " + "\"" + passwordInput + "\"}";
        final String url = Constants.baseURL + Constants.usersPath;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HandlerService.setVisibility(relativeLayoutProgressBar, View.VISIBLE);
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
                        HandlerService.makeToast(ctx, "Something went wrong.", Toast.LENGTH_SHORT, 500);
                    }else{
                        if(response.code() == 200){
                            JSONObject object  = ReaderService.toJSONObject(response.body().byteStream());
                            Log.i("information", object.getString("UserID"));
                            Log.i("information", String.valueOf(object.length()));
                            Log.i("information", object.toString());
                            UserModel.getInstance();
                            UserModel.setEmail(emailInput);
                            UserModel.setPassword(passwordInput);
                            UserModel.setUserID((Integer.parseInt(object.getString("UserID"))));
                            JSONArray jsonArray = object.getJSONArray("ProductsConcrete");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                Product product = new Product(jObject.getString("Name")
                                        , jObject.getString("ExpiryDate")
                                        , jObject.getString("Id"));
                                UserModel.addProduct(product);
                            }
                            HandlerService.makeToast(ctx, "Success!", Toast.LENGTH_SHORT, 500);
                            ctx.startActivity(new Intent(ctx, MainActivity.class));
                        }else{
                            HandlerService.makeToast(ctx, "Something went wrong.", Toast.LENGTH_SHORT, 500);
                            Log.i("informationsignupcode", String.valueOf(response.code()));
                        }
                    }

                }catch (Exception e){
                    Log.e("information", e.toString());
                    HandlerService.makeToast(ctx, "Something went wrong.", Toast.LENGTH_SHORT, 500);
                }
                HandlerService.setVisibility(relativeLayoutProgressBar, View.GONE, 500);
            }
        });
    }

    public void GetFridgeObjects(final Context ctx, final String emailInput, final String passwordInput){
        final String url = Constants.baseURL + Constants.productsPath;
        final String json = "{\"UserID\": " + "\"" + String.valueOf(UserModel.getUserID()) + "\","
             + "}";
        // final String json = "{\"UserID\":33,\"EAN\":123123123,\"FoodTypeName\":\"Milk\",\"ExpirationDate\":\"2018-10-06\"}";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try{
                    URL loginURL = new URL(Constants.baseURL + Constants.usersPath + "/?email=" + emailInput + "&password=" + passwordInput);

                    // Create connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                    if(httpURLConnection.getResponseCode() == 200){

                        // Assign Products??
                    }

                }catch (Exception e){
                    Log.e("information", e.toString());
                }
            }
        });
    }
}


