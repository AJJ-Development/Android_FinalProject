package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamehub.adapters.GameAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;

import okhttp3.Headers;

public class GameListActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.rawg.io/api/games?key=bb55f483d7464f99917c8e1f821f9cfc";
    private BottomNavigationView bottomNavigationView;
    private TextView tvListTitle;
    public static final String TAG = "GameListActivity";

    List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        RecyclerView rvGames = findViewById(R.id.rvGames);
        games = new ArrayList<>();
        tvListTitle = findViewById(R.id.tvListTitle);

        //Create the adapter
        GameAdapter gameAdapter = new GameAdapter(this, games);

        //Set the adapter on the recycler view
        rvGames.setAdapter(gameAdapter);

        //Set a Layout Manager on the recycler view
        rvGames.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        String btnClicked = extras.getString("type");
        //queryGames(btnClicked, gameAdapter);
        tvListTitle.setText(btnClicked.toUpperCase().replace("_", " "));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    games.addAll(Game.fromJsonArray(results));
                    gameAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Games: " + games.size());
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure");
            }
        });


        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Button Click Listeners ----------------------------------
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
                    case R.id.action_profile:
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

//    private void queryGames(String btnClicked, GameAdapter adapter) {
//        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
//        query.whereEqualTo(btnClicked, true);
//        query.setLimit(20);
//        query.addDescendingOrder(Game.KEY_CREATED_AT);
//        query.findInBackground(new FindCallback<Game>() {
//            @Override
//            public void done(List<Game> gamesList, ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Issue getting games", e);
//                }
//                games.addAll(gamesList);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
}