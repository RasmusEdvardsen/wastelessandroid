package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.UserModel;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.HandlerService;
import com.example.edvardsen.wastelessclient.services.ReaderService;

import org.json.JSONObject;


import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity {
    RelativeLayout relativeLayout;
    EditText email;
    EditText password;
    Button login;
    Button signup;
    RelativeLayout relativeLayoutProgressBar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: NEW CLASS THAT CHECK IF UTIL.GETTOKEN THEN START MAIN ACT INSTEAD OF LOGIN ACT

        relativeLayout = findViewById(R.id.activity_login_relativelayout);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);
        relativeLayoutProgressBar = findViewById(R.id.activity_login_relativelayout_progressbar);
        progressBar = findViewById(R.id.activity_login_progressbar);
        progressBar.setIndeterminate(true);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();

                //Login flow start.
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
                                Log.i("information", jsonObject.toString());
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                            }
                        }catch (Exception e){
                            Log.e("information", e.toString());
                            HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                        }
                        HandlerService.setVisibility(relativeLayoutProgressBar, View.GONE, 500);
                    }
                });
                //Login flow end.
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();
                final String json = "{\"email\": " + "\"" + emailInput + "\","
                        + "\"password\": " + "\"" + passwordInput + "\"}";
                final String url = Constants.baseURL + Constants.usersPath;

                //Signup flow start.
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
                                HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
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
                                    HandlerService.makeToast(getBaseContext(), "Success!", Toast.LENGTH_SHORT, 500);
                                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                                }else{
                                    HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                                    Log.i("informationsignupcode", String.valueOf(response.code()));
                                }
                            }

                        }catch (Exception e){
                            Log.e("information", e.toString());
                            HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                        }
                        HandlerService.setVisibility(relativeLayoutProgressBar, View.GONE, 500);
                    }
                });
                //Signup flow end.
            }
        });
    }
}
