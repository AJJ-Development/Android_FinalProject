package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // Button Click Listeners ----------------------------------
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                LoginUser(username, password);

            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Create Account Button");
                goCreateAccountActivity();
            }
        });
    }

    // TODO: Write logic for Login User with backend Parse
    private void LoginUser(String username, String password) {
        Log.i(TAG, "Username: " + username);
        Log.i(TAG, "Password: " + password);

        // Check for empty Username and password
        if (username.equals("")) {
            Toast.makeText(LoginActivity.this, "Username Required", Toast.LENGTH_SHORT).show();
        }
        else if (password.equals("")) {
            Toast.makeText(LoginActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
        }
        else {
            goMainActivity();
        }
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void goCreateAccountActivity() {
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }
}