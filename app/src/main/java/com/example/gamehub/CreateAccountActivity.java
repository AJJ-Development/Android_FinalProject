package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String TAG = "CreateAccountActivity";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordVerify;
    private EditText etEmail;
    private Button btnCreateAcc;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize Layout Objects -------------------------------
        etUsername = findViewById(R.id.etUsernameCA);
        etPassword = findViewById(R.id.etPasswordCA);
        etPasswordVerify = findViewById(R.id.etPasswordVerifyCA);
        etEmail = findViewById(R.id.etEmailCA);
        btnCreateAcc = findViewById(R.id.btnCreateAccCA );
        btnLogin = findViewById(R.id.btnLoginCA);

        // Button Click Listeners ----------------------------------
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Login button clicked, send user back to login");
                goLoginActivity();
            }
        });


    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}