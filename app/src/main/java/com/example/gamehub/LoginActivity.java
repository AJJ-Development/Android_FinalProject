package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCreateAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize layout objects -------------------------------
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAcc = findViewById(R.id.btnCreateAcc);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        // Button Click Listeners ----------------------------------
        //-------------------- LOGIN BUTTON ----------------------//
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                String emptyField = "";
                // Populate emptyField with errors
                if (username.equals("")) { emptyField = "Username"; }
                else if (password.equals("")) { emptyField = "Password"; }

                // If there there is an error, display it to the user, otherwise login
                if (!emptyField.equals("")) {
                    Toast.makeText(LoginActivity.this, emptyField+" Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(username, password);
                }

            }
        });

        //-------------------- SIGNUP BUTTON ----------------------//
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Create Account Button");

                goCreateAccountActivity();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Logging user in -- Username: " + username + " Password: " + password);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        overridePendingTransition(0, 0);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }

    private void goCreateAccountActivity() {
        Intent i = new Intent(this, CreateAccountActivity.class);
        overridePendingTransition(0, 0);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }
}