package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamehub.adapters.GameAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import java.util.ArrayList;

import okhttp3.Headers;

public class GameListActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.rawg.io/api/games?key=bb55f483d7464f99917c8e1f821f9cfc";
    public static final String TOP_RATED_QUERY = "&metacritic=80";
    public static final String NEW_RELEASES_QUERY = "&dates=2021-01-01,2021-12-31";
    public static final String MULTIPLAYER_QUERY = "&tags=multiplayer";
    public static final String SINGLEPLAYER_QUERY = "&tags=singleplayer";
    public static final String PC_ONLY_QUERY = "&platforms_count=1&platforms=4"; //4
    public static final String XB_ONLY_QUERY = "&platforms_count=1&platforms=1"; //1
    public static final String PS_ONLY_QUERY = "&platforms_count=1&platforms=7"; //
    public static final String iOS_ONLY_QUERY = "&platforms_count=1&platforms=3";//3
    public static String NEXT_PAGE_URL = "";
    public static String GAMES_LIST_URL = BASE_URL;
    private BottomNavigationView bottomNavigationView;
    private TextView tvListTitle;
    public static final String TAG = "GameListActivity";

    GameAdapter gameAdapter;

    AsyncHttpClient client = new AsyncHttpClient();

    List<Game> apiGames;
    List<Game> games;

    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        GAMES_LIST_URL = BASE_URL;

        RecyclerView rvGames = findViewById(R.id.rvGames);
        apiGames = new ArrayList<>();
        games = new ArrayList<>();
        tvListTitle = findViewById(R.id.tvListTitle);

        //Create the adapter
        gameAdapter = new GameAdapter(this, games);

        //Set the adapter on the recycler view
        rvGames.setAdapter(gameAdapter);

        //Set a Layout Manager on the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvGames.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        String btnClicked = extras.getString("type");

        if (btnClicked.equalsIgnoreCase("top_rated")) {
            GAMES_LIST_URL = GAMES_LIST_URL + TOP_RATED_QUERY;
        }
        else if (btnClicked.equalsIgnoreCase("new_release")) {
            GAMES_LIST_URL = GAMES_LIST_URL + NEW_RELEASES_QUERY;
        }
        else if (btnClicked.equalsIgnoreCase("liked_games")) {
            //getLikedGames();
        }
        else if(btnClicked.equalsIgnoreCase("multiplayer")) {
            GAMES_LIST_URL = GAMES_LIST_URL + MULTIPLAYER_QUERY;
        }
        else if(btnClicked.equalsIgnoreCase("singleplayer")) {
            GAMES_LIST_URL = GAMES_LIST_URL + SINGLEPLAYER_QUERY;
        }
        else if(btnClicked.equalsIgnoreCase("pc_only")) {
            GAMES_LIST_URL = GAMES_LIST_URL + PC_ONLY_QUERY;
        }
        else if(btnClicked.equalsIgnoreCase("xbox_only")) {
            GAMES_LIST_URL = GAMES_LIST_URL + XB_ONLY_QUERY;
        }
        else if(btnClicked.equalsIgnoreCase("switch_only")) {
            GAMES_LIST_URL = GAMES_LIST_URL + PS_ONLY_QUERY;
        }
        else if(btnClicked.equalsIgnoreCase("ios_only")) {
            GAMES_LIST_URL = GAMES_LIST_URL + iOS_ONLY_QUERY;
        }

        tvListTitle.setText(btnClicked.toUpperCase().replace("_", " "));

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!btnClicked.equalsIgnoreCase("liked_games")) {
                    loadMoreData();
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        rvGames.addOnScrollListener(scrollListener);

        client.get(GAMES_LIST_URL, new JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    NEXT_PAGE_URL = jsonObject.getString("next");
                    gameAdapter.clear();
                    games.addAll(Game.fromJsonArray(results));
                    apiGames.addAll(games);
                    if (!btnClicked.equalsIgnoreCase("liked_games")) {
                        Log.i(TAG, "populating");
                        gameAdapter.notifyDataSetChanged();
                    }
                    else {
                        getLikedGames();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
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

    private void getLikedGames() {
        String currUsername = ParseUser.getCurrentUser().getUsername();
        ParseQuery<LikedGames> query = ParseQuery.getQuery(LikedGames.class);
        query.include(LikedGames.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(LikedGames.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<LikedGames>() {
            @Override
            public void done(List<LikedGames> likedGames, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                }
                gameAdapter.clear();
                for (LikedGames likedGame : likedGames) {
                    for (Game game : apiGames) {
                        if (likedGame.getGameId().equals(game.getGameId()) && currUsername.equals(likedGame.getUser().getUsername())) {
                            games.add(game);
                        }
                    }
                }
                gameAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadMoreData() {
        client.get(NEXT_PAGE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    NEXT_PAGE_URL = jsonObject.getString("next");
                    List<Game> games = Game.fromJsonArray(results);
                    gameAdapter.addAll(games);
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure for loadMoreData!", throwable);
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