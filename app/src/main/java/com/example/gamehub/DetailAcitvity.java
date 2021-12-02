package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailAcitvity extends AppCompatActivity {

    public static final String TAG = "DetailAcitivity";
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

        tvDetGameTitle = findViewById(R.id.tvDetGameTitle);
        tvDetGameDesc = findViewById(R.id.tvDetGameDesc);
        ratingBar = findViewById(R.id.ratingBar);
        ivGameImage = findViewById(R.id.ivGameImage);
        btnLikeGame = findViewById(R.id.btnLikeGame);

        //Unwrap the game object that is sent to the details page
        Game game = Parcels.unwrap(getIntent().getParcelableExtra("game"));

        tvDetGameTitle.setText(game.getKeyName());
        tvDetGameDesc.setText(game.getKeyDesc());
        ParseFile image = game.getKeyImage();
        if (image != null) {
            Glide.with(DetailAcitvity.this).load(image.getUrl()).apply(new RequestOptions().override(1000, 500)).into(ivGameImage);
        }
        ratingBar.setRating((float) game.getKeyRating());

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