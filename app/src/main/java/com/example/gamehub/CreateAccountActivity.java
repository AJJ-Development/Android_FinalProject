package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
        etUsername.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(14) });
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

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Create account button clicked, create acc and login");

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordVerify = etPasswordVerify.getText().toString();
                String email = etEmail.getText().toString();

                String emptyField = "";
                // Populate emptyField with errors
                if (username.equals("")) { emptyField = "Username"; }
                else if (password.equals("")) { emptyField = "Password"; }
                else if (passwordVerify.equals("")) { emptyField = "Password verification"; }
                else if (email.equals("")) { emptyField = "Email"; }

                // If there is an error display it to the user, otherwise login
                if (!emptyField.equals("")) {
                    Toast.makeText(CreateAccountActivity.this, emptyField+" Required", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(passwordVerify)) {
                    Toast.makeText(CreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    createUser(username, password, email);
                }
            }
        });


    }

    private void createUser(String username, String password, String email) {
        Log.i(TAG, "Create user");

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("name", username);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // TODO: better error handling
                    Log.e(TAG, "Issue with sign-up", e);
                    Toast.makeText(CreateAccountActivity.this, "Issue with sign-up!", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        overridePendingTransition(0, 0);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        overridePendingTransition(0, 0);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}