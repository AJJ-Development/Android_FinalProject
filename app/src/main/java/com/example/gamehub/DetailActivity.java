package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    private static final String API_KEY = "?key=bb55f483d7464f99917c8e1f821f9cfc";
    private static String BASE_URL = "https://api.rawg.io/api/games/";
    private String id = "";
    private static String GAME_DETAIL_URL = "";
    private String overview = "";
    private BottomNavigationView bottomNavigationView;
    TextView tvDetGameTitle;
    TextView tvDetGameDesc;
    RatingBar ratingBar;
    ImageView ivGameImage;
    Button btnLikeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acitvity);

        GAME_DETAIL_URL = BASE_URL;

        tvDetGameTitle = findViewById(R.id.tvDetGameTitle);
        tvDetGameDesc = findViewById(R.id.tvDetGameDesc);
        ratingBar = findViewById(R.id.ratingBar);
        ivGameImage = findViewById(R.id.ivGameImage);
        btnLikeGame = findViewById(R.id.btnLikeGame);

        //Unwrap the game object that is sent to the details page
        Game game = Parcels.unwrap(getIntent().getParcelableExtra("game"));

        id = game.getGameId();
        Log.i(TAG, "API Endpoint: " + GAME_DETAIL_URL);
        GAME_DETAIL_URL = GAME_DETAIL_URL + id + API_KEY;
        Log.i(TAG, "API Endpoint: " + GAME_DETAIL_URL);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GAME_DETAIL_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    tvDetGameDesc.setText(jsonObject.getString("description_raw"));
                    tvDetGameTitle.setText(game.getTitle());
                    Glide.with(DetailActivity.this)
                            .load(game.getImage())
                            .apply(new RequestOptions()
                                    .override(1000, 500)
                                    .placeholder(R.drawable.placeholder))
                            .into(ivGameImage);
                    ratingBar.setRating((float) game.getRating());
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

        //-------------------- LIKE GAME CLICKED ----------------------//
        btnLikeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    if(game is not liked) {
                        //Like the game
                        //Increment liked game in database
                    }
                    else {
                        //Unlike the game
                        //Decrement liked game in database
                    }
                 */
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

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}