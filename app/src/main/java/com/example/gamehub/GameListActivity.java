package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.gamehub.adapters.GameAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static final String TAG = "GameListActivity";

    List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        RecyclerView rvGames = findViewById(R.id.rvGames);
        games = new ArrayList<>();

        //Create the adapter
        GameAdapter gameAdapter = new GameAdapter(this, games);

        //Set the adapter on the recycler view
        rvGames.setAdapter(gameAdapter);

        //Set a Layout Manager on the recycler view
        rvGames.setLayoutManager(new LinearLayoutManager(this));

        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Button Click Listeners ----------------------------------
        //--------------- NAV BUTTON CLICKED ----------------- //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(GameListActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                        goMainActivity();
                        break;
                    case R.id.action_search:
                        Toast.makeText(GameListActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        goSearchActivity();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(GameListActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                        goProfileActivity();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
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

    private void goProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }
}