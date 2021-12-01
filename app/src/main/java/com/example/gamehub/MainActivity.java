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

import com.google.android.material.bottomnavigation.BottomNavigationView;


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

        /*IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials("s5co5sej9ddx485f36bovcuiku3v5a", "4svc3xcddy9obdxyfbs05ny4mupdrn");
        APICalypse apicalypse = new APICalypse().fields("*").sort("release_dates.date", Sort.DESCENDING);
        try{
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            Log.i(TAG, "games: " + games);
        } catch(RequestException e) {
            Log.e(TAG, "games: " + e.getMessage());
        }*/

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

        //-------------------- TRENDING BUTTON ----------------------//
        btnTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Trending Button");
                goGameListActivity("trending");
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