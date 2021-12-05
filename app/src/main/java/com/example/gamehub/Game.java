package com.example.gamehub;

import android.util.Log;

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
    String image;
    float rating;
    float[] ratingsArr = new float[4];
    String releaseDate;
    String overview = "Release Date: ";

    public Game() {}

    public Game(JSONObject jsonObject) throws JSONException {
        gameId = jsonObject.getString("id");
        title = jsonObject.getString("name");
        image = jsonObject.getString("background_image");
        rating = (float)jsonObject.getDouble("rating");
        releaseDate = jsonObject.getString("released");
        overview = overview.concat(jsonObject.getString("released") + "\n" + "Platforms: ");

        JSONArray platformsList = jsonObject.getJSONArray("platforms");
        JSONArray ratingsList = jsonObject.getJSONArray("ratings");

        for (int i = 0; i < platformsList.length(); i++) {
            String platform = platformsList.getJSONObject(i).getJSONObject("platform").getString("name");

            overview = overview.concat(platform + " ");
        }

        for (int i = 0; i < ratingsList.length(); i++) {
            ratingsArr[i] = (float)ratingsList.getJSONObject(i).getDouble("percent");
        }
    }

    public static List<Game> fromJsonArray(JSONArray gameJsonArray) throws JSONException {
        List<Game> games = new ArrayList<>();
        for( int i = 0; i < gameJsonArray.length(); i++) {
            games.add(new Game(gameJsonArray.getJSONObject(i)));
        }
        return games;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public float getRating() {
        return rating;
    }

    public float[] getRatingsArr() { return ratingsArr; }

    public String getOverview() { return overview; }
}