package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.utils.Endpoints;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.List;

import proto.Game;
import proto.GameResult;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    private Button btnTopRated;
    private Button btnTrending;
    private Button btnNewReleases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials("tdwium5zu2nu08w9y887u5x74t6wh5", "Bearer lgs0h22z8l2u2ior4w54ne0617n8ap");
        APICalypse apicalypse = new APICalypse().fields("*")
                .sort("release_dates.date", Sort.DESCENDING).where("themes != 42");
        try{
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            Log.i(TAG, "games: LETSFUCKINGGOOOOOOOOOOO");
        } catch(RequestException e) {
            Log.e(TAG, "games: " + e.getMessage());
        }

//        byte[] bytes = new byte[0];
//        try {
//            bytes = wrapper.apiProtoRequest(Endpoints.GAMES, "fields *;");
//        } catch (RequestException e) {
//            e.printStackTrace();
//        }
//        List<Game> listOfGames = GameResult.parseFrom(bytes).getGamesList();
//
//        for (Game i : listOfGames) {
//            Log.i(TAG, "game: " + i);
//        }


        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnTopRated = findViewById(R.id.btnTopRated);
        btnTrending = findViewById(R.id.btnTrending);
        btnNewReleases = findViewById(R.id.btnNewReleases);

        // Button Click Listeners ----------------------------------
        //--------------- NAV BUTTON CLICKED ----------------- //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        goSearchActivity();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
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
                goGameListActivity();
            }
        });

        //-------------------- TRENDING BUTTON ----------------------//
        btnTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Trending Button");
                goGameListActivity();
            }
        });

        //-------------------- NEW RELEASES BUTTON ----------------------//
        btnNewReleases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick New Releases Button");
                goGameListActivity();
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

    private void goGameListActivity() {
        Intent i = new Intent(this, GameListActivity.class);
        startActivity(i);
        finish();
    }
}