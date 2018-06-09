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
import com.example.edvardsen.wastelessclient.data.FridgeObjects;
import com.example.edvardsen.wastelessclient.data.Product;
import com.example.edvardsen.wastelessclient.data.UserModel;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.AsyncTaskService;
import com.example.edvardsen.wastelessclient.services.HandlerService;
import com.example.edvardsen.wastelessclient.services.ReaderService;

import org.json.JSONArray;
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
    AsyncTaskService asyncTaskService;

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
        asyncTaskService = new AsyncTaskService();

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();
                asyncTaskService.LoginFlow(getBaseContext(), relativeLayoutProgressBar, emailInput, passwordInput);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();
                asyncTaskService.SignUpFlow(getBaseContext(), relativeLayoutProgressBar, emailInput, passwordInput);
            }
        });
    }
}
