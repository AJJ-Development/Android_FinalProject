package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";
    public static final String NOW_PLAYING_URL = "https://api.rawg.io/api/games?key=bb55f483d7464f99917c8e1f821f9cfc&page_size=50";
    private BottomNavigationView bottomNavigationView;
    private RecyclerView rvGames;
    private EditText etSearchGame;
    private TextWatcher textWatcher;
    List<Game> games;
    List<Game> gamesCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rvGames = findViewById(R.id.rvGames_Search);
        etSearchGame = findViewById(R.id.etSearchGame);

        etSearchGame.requestFocus();

        games = new ArrayList<>();
        gamesCopy = new ArrayList<>();

        //Create the adapter
        GameAdapter gameAdapter = new GameAdapter(this, games);

        //Set the adapter on the recycler view
        rvGames.setAdapter(gameAdapter);

        //Set a Layout Manager on the recycler view
        rvGames.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    gamesCopy.addAll(Game.fromJsonArray(results));
                    //gameAdapter.notifyDataSetChanged();
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

        textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) { gameAdapter.clear(); return; }
                gameAdapter.clear();

                for (int i = 0; i < gamesCopy.size(); i++) {
                    if (gamesCopy.get(i).getTitle().toLowerCase().startsWith(editable.toString().toLowerCase())) {
                        if (!games.contains(gamesCopy.get(i))) {
                            games.add(gamesCopy.get(i));
                        }
                    }
                }
                gameAdapter.notifyDataSetChanged();
            }
        };

        etSearchGame.addTextChangedListener(textWatcher);

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

    private void goProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}