package com.example.gamehub;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Game")
public class Game extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_RATING = "rating";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TRENDING = "trending";
    public static final String KEY_TOP_RATED = "top_rated";
    public static final String KEY_NEW_RELEASE = "new_release";

    public String getKeyName() {
        return getString(KEY_NAME);
    }

    public String getKeyDesc() {
        return getString(KEY_DESC);
    }

    public String getKeyRating() {
        return getString(KEY_RATING);
    }

    public ParseFile getKeyImage() {
        return getParseFile(KEY_IMAGE);
    }

    public boolean getKeyTrending() { return getBoolean(KEY_TRENDING); }

    public boolean getKeyTopRated() { return getBoolean(KEY_TOP_RATED); }

    public boolean getKeyNewRelease() { return getBoolean(KEY_NEW_RELEASE); }
}
