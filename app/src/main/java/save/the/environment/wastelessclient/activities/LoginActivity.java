package save.the.environment.wastelessclient.activities;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import save.the.environment.wastelessclient.services.AsyncTaskService;

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
        setContentView(save.the.environment.wastelessclient.R.layout.activity_login);

        //TODO: NEW CLASS THAT CHECK IF UTIL.GETTOKEN THEN START MAIN ACT INSTEAD OF LOGIN ACT

        relativeLayout = findViewById(save.the.environment.wastelessclient.R.id.activity_login_relativelayout);
        email = findViewById(save.the.environment.wastelessclient.R.id.email);
        password = findViewById(save.the.environment.wastelessclient.R.id.password);
        login = findViewById(save.the.environment.wastelessclient.R.id.loginButton);
        signup = findViewById(save.the.environment.wastelessclient.R.id.signupButton);
        relativeLayoutProgressBar = findViewById(save.the.environment.wastelessclient.R.id.activity_login_relativelayout_progressbar);
        progressBar = findViewById(save.the.environment.wastelessclient.R.id.activity_login_progressbar);
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
