package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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
    ImageView ivLikeGame;
    PieChart pieChart;
    float[] ratingsArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acitvity);

        GAME_DETAIL_URL = BASE_URL;

        tvDetGameTitle = findViewById(R.id.tvDetGameTitle);
        tvDetGameDesc = findViewById(R.id.tvDetGameDesc);
        ratingBar = findViewById(R.id.ratingBar);
        ivGameImage = findViewById(R.id.ivGameImage);
        ivLikeGame = findViewById(R.id.ivLikeGame);
        pieChart = findViewById(R.id.pieChart);

        //Unwrap the game object that is sent to the details page
        Game game = Parcels.unwrap(getIntent().getParcelableExtra("game"));

        pieChart.setHoleRadius(20);
        pieChart.setCenterText("Reviews");
        pieChart.setRotationEnabled(true);
        pieChart.setTransparentCircleAlpha(0);

        ArrayList<PieEntry> yList = new ArrayList<>();
        ArrayList<String> xList = new ArrayList<>();

        String[] xArr = {"Excellent", "Good", "Bad", "Terrible"};
        ratingsArr = new float[4];

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
                    getLikedGame(game.getGameId(), ParseUser.getCurrentUser());
                    tvDetGameDesc.setText(jsonObject.getString("description_raw"));
                    tvDetGameTitle.setText(game.getTitle());
                    Glide.with(DetailActivity.this)
                            .load(game.getImage())
                            .apply(new RequestOptions()
                                    .override(1000, 500)
                                    .placeholder(R.drawable.placeholder))
                            .into(ivGameImage);
                    ratingBar.setRating((float) game.getRating());
                    ratingsArr = game.getRatingsArr();
                    for (int i = 0; i < 4; i++) {
                        Log.i(TAG, "Rating # " + i + ": " + ratingsArr[i]);
                        yList.add(new PieEntry(ratingsArr[i], i));
                        xList.add(xArr[i]);
                    }

                    //Create data set
                    PieDataSet pieDataSet = new PieDataSet(yList, "Ratings");
                    pieDataSet.setSliceSpace(2);
                    pieDataSet.setValueTextSize(12);

                    //Add colors to dataset
                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(getResources().getColor(R.color.excellent));
                    colors.add(getResources().getColor(R.color.good));
                    colors.add(getResources().getColor(R.color.bad));
                    colors.add(getResources().getColor(R.color.terrible));

                    pieDataSet.setColors(colors);

                    //Add legend to chart
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.invalidate();

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
        ivLikeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the heart icon is not filled in//
                if (ivLikeGame.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24).getConstantState()) {
                    //Fill in the heart icon//
                    ivLikeGame.setImageResource(R.drawable.ic_baseline_favorite_24);
                    likeGame(game.getGameId(), ParseUser.getCurrentUser());
                }
                else {
                    //Unfill the heart icon//
                    ivLikeGame.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    unlikeGame(game.getGameId(), ParseUser.getCurrentUser());
                }
            }
        });

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1 = e.toString().indexOf("y: ");
                String percentage = e.toString().substring(pos1 + 3);

                for(int i = 0; i < ratingsArr.length; i++) {
                    if(ratingsArr[i] == Float.parseFloat(percentage)) {
                        pos1 = i;
                        break;
                    }
                }
                String ratingType = xArr[pos1];
                Toast.makeText(DetailActivity.this, "Rating: " + ratingType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void unlikeGame(String gameId, ParseUser user) {
        ParseQuery<LikedGames> query = ParseQuery.getQuery(LikedGames.class);
        query.include(LikedGames.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(LikedGames.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<LikedGames>() {
            @Override
            public void done(List<LikedGames> likedGames, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting games", e);
                }
                for (LikedGames likedGame : likedGames) {
                    if (likedGame.getGameId().equals(gameId) && likedGame.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                        likedGame.put("user", ParseUser.createWithoutData("_User", "NGrmdkbeBK"));
                        likedGame.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.e(TAG, "Error while saving", e);
                                }
                                Log.i(TAG, "Game disliked successfully!");
                            }
                        });
                    }
                }
            }
        });
    }

    private void likeGame(String gameId, ParseUser user) {
        LikedGames likedGame = new LikedGames();
        likedGame.setGameId(gameId);
        likedGame.setUser(user);
        likedGame.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                }
                Log.i(TAG, "Game liked successfully!");
            }
        });
    }

    private void getLikedGame(String gameId, ParseUser user) {
        ParseQuery<LikedGames> query = ParseQuery.getQuery(LikedGames.class);
        query.include(LikedGames.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(LikedGames.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<LikedGames>() {
            @Override
            public void done(List<LikedGames> likedGames, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting games", e);
                }
                for (LikedGames likedGame : likedGames) {
                    if (likedGame.getGameId().equals(gameId) && likedGame.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                        ivLikeGame.setImageResource(R.drawable.ic_baseline_favorite_24);
                    }
                }
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