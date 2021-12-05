package com.example.gamehub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";
    private Button btnLogout;
    private BottomNavigationView bottomNavigationView;
    private TextView tvProfileNickname;
    private TextView tvProfileEmail;
    private EditText etNewNickname;
    private Button btnSetNewNickname;
    private Button btnLikedGames;
    private Button btnChangeProfPic;
    private ImageView ivProfilePic;
    private ParseUser currentUser = ParseUser.getCurrentUser();

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private File photoFile;
    public String photoFileName = "photo.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize layout objects -------------------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnLogout = findViewById(R.id.btnLogout);
        tvProfileNickname = findViewById(R.id.tvProfileNickname);
        setProfileNickname();
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileEmail.setText(currentUser.get("email").toString());
        etNewNickname = findViewById(R.id.etNewNickname);
        etNewNickname.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(14) });
        btnSetNewNickname = findViewById(R.id.btnSetNewNickname);
        btnLikedGames = findViewById(R.id.btnLikedGames);
        btnChangeProfPic = findViewById(R.id.btnChangeProfPic);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        ParseFile iconImage = currentUser.getParseFile("profilePic");
        iconImage.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // Decode the Byte[] into
                    // Bitmap
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                    // Set the Bitmap into the
                    // ImageView
                    ivProfilePic.setImageBitmap(bmp);

                } else {
                    Log.d("test", "Problem load image the data.");
                }
            }
        });

        // Button Click Listeners ----------------------------------

        //--------------- CHANGE PROFILE PICTURE CLICKED ----------------- //
        btnChangeProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        //--------------- LOGOUT CLICKED ----------------- //
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Log out user");
                Toast.makeText(ProfileActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                goLoginActivity();
            }
        });

        //-------------- VIEW LIKED GAMES CLICKED ------------------ //
        btnLikedGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGameListActivity("liked_games");
            }
        });

        //--------------- SET NEW USERNAME CLICKED ----------------- //
        btnSetNewNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = etNewNickname.getText().toString();
                if (newUsername.equals("")) {
                    Toast.makeText(ProfileActivity.this, "Enter new name", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentUser.put("name", newUsername);
                    currentUser.saveInBackground(e -> {
                        if (e == null) {
                            setProfileNickname();
                            etNewNickname.setText("");
                        } else {
                            Log.i(TAG, "Error", e);
                        }
                    });
                }
            }
        });

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
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(ProfileActivity.this, "com.codepath.fileprovider2", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                ivProfilePic.setImageBitmap(takenImage);
                currentUser.put("profilePic", new ParseFile(photoFile));
                currentUser.saveInBackground(e -> {
                    if (e != null) {
                        Log.i(TAG, "Error Setting Profile Picture", e);
                    }
                });
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    // Set Profile nickname - Function so it can be recalled/refreshed
    private void setProfileNickname() {
        tvProfileNickname.setText(currentUser.get("name").toString().toUpperCase(Locale.ROOT));
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

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
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