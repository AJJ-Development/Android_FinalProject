package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";
    private Button btnLogout;
    private BottomNavigationView bottomNavigationView;
    private TextView tvProfileNickname;
    private TextView tvProfileEmail;
    private EditText etNewNickname;
    private Button btnSetNewNickname;
    private Button btnLikedGames;
    private ParseUser currentUser = ParseUser.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnLogout = findViewById(R.id.btnLogout);
        tvProfileNickname = findViewById(R.id.tvProfileNickname);
        setProfileNickname();
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileEmail.setText(currentUser.get("email").toString());
        etNewNickname = findViewById(R.id.etNewNickname);
        etNewNickname.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(14) });
        btnSetNewNickname = findViewById(R.id.btnSetNewNickname);
        btnLikedGames = findViewById(R.id.btnLikedGames);

        // Button Click Listeners ----------------------------------

        //--------------- LOGOUT CLICKED ----------------- //
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Log out user");
                Toast.makeText(ProfileActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                goLoginActivity();
            }
        });

        //-------------- VIEW LIKED GAMES CLICKED ------------------ //
        btnLikedGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGameListActivity("liked_games");
            }
        });

        //--------------- SET NEW USERNAME CLICKED ----------------- //
        btnSetNewNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = etNewNickname.getText().toString();
                if (newUsername.equals("")) {
                    Toast.makeText(ProfileActivity.this, "Enter new name", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentUser.put("name", newUsername);
                    currentUser.saveInBackground(e -> {
                        if (e == null) {
                            setProfileNickname();
                            etNewNickname.setText("");
                        } else {
                            Log.i(TAG, "Error", e);
                        }
                    });
                }
            }
        });

        //--------------- NAV BUTTON CLICKED ----------------- //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        goMainActivity();
                        break;
                    case R.id.action_search:
                        goSearchActivity();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    // Set Profile nickname - Function so it can be recalled/refreshed
    private void setProfileNickname() {
        tvProfileNickname.setText(currentUser.get("name").toString().toUpperCase(Locale.ROOT));
    }

    private void goSearchActivity() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
        finish();
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void goGameListActivity(String type) {
        Intent i = new Intent(this, GameListActivity.class);
        i.putExtra("type", type);
        startActivity(i);
        finish();
    }
}