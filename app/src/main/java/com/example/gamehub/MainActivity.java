package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    private Button btnTopRated;
    private Button btnNewReleases;
    private Spinner spnPlatform;
    private Spinner spnStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnTopRated = findViewById(R.id.btnTopRated);
        btnNewReleases = findViewById(R.id.btnNewReleases);
        spnPlatform = findViewById(R.id.spnPlatform);
        spnStyle = findViewById(R.id.spnPlayers);

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

        //-------------------- PLATFORM DROPDOWN ----------------------//
        spnPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "spinner: " + i);
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                switch (i) {
                    case 1:
                        goGameListActivity("pc_only");
                        break;
                    case 2:
                        goGameListActivity("xbox_only");
                        break;
                    case 3:
                        goGameListActivity("switch_only");
                        break;
                    case 4:
                        goGameListActivity("ios_only");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-------------------- STYLE DROPDOWN ----------------------//
        spnStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "spinner: " + i);
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                switch (i) {
                    case 1:
                        goGameListActivity("multiplayer");
                        break;
                    case 2:
                        goGameListActivity("singleplayer");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void goSearchActivity() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    private void goProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    private void goGameListActivity(String type) {
        Intent i = new Intent(this, GameListActivity.class);
        i.putExtra("type", type);
        startActivity(i);
    }
}