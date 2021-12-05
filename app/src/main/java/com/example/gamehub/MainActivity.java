package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    private Button btnTopRated;
    private Button btnNewReleases;
    private Button btnPCOnly;
    private Button btnMultiPlatGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnTopRated = findViewById(R.id.btnTopRated);
        btnNewReleases = findViewById(R.id.btnNewReleases);
        btnPCOnly = findViewById(R.id.btnPCOnly);
        btnMultiPlatGames = findViewById(R.id.btnMultiPlayGames);

        // Button Click Listeners ----------------------------------
        //--------------- NAV BUTTON CLICKED ----------------- //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        goSearchActivity();
                        break;
                    case R.id.action_profile:
                        goProfileActivity();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //Button Click Listeners -------------------------
        //-------------------- TOP RATED BUTTON ----------------------//
        btnTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Top Rated Button");
                goGameListActivity("top_rated");
            }
        });

        //-------------------- NEW RELEASES BUTTON ----------------------//
        btnNewReleases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick New Releases Button");
                goGameListActivity("new_release");
            }
        });

        //-------------------- PC Only BUTTON ----------------------//
        btnPCOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick PC Only Button");
                goGameListActivity("pc_only");
            }
        });

        //-------------------- Multi-Platform BUTTON ----------------------//
        btnMultiPlatGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Multiplayer Button");
                goGameListActivity("multiplayer");
            }
        });
    }

    private void goSearchActivity() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
        finish();
    }

    private void goProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
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