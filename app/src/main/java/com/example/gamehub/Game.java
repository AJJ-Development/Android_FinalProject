package com.example.gamehub;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Game {

    String gameId;
    String title;
    String overview;
    String image;
    String slug;
    float rating;

    public Game() {}

    public Game(JSONObject jsonObject) throws JSONException {
        gameId = jsonObject.getString("id");
        title = jsonObject.getString("name");
        image = jsonObject.getString("background_image");
        slug = jsonObject.getString("slug");
        //overview = jsonObject.getString("overview");
        overview = "This is some test data This is some test data This is some test data This is some test dataThis is some test dataThis is some test dataThis is some test dataThis is some test dataThis is some test data";
        rating = (float)jsonObject.getDouble("rating");
    }

    public static List<Game> fromJsonArray(JSONArray gameJsonArray) throws JSONException {
        List<Game> movies = new ArrayList<>();
        for( int i = 0; i < gameJsonArray.length(); i++) {
            movies.add(new Game(gameJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getImage() {
        return image;
    }

    public String getSlug() {
        return slug;
    }

    public float getRating() {
        return rating;
    }

//    public static final String KEY_NAME = "name";
//    public static final String KEY_DESC = "description";
//    public static final String KEY_RATING = "rating";
//    public static final String KEY_IMAGE = "image";
//
//    public String getKeyName() {
//        return getString(KEY_NAME);
//    }
//
//    public String getKeyDesc() {
//        return getString(KEY_DESC);
//    }
//
//    public double getKeyRating() {
//        return getDouble(KEY_RATING);
//    }
//
//    public ParseFile getKeyImage() {
//        return getParseFile(KEY_IMAGE);
//    }
}