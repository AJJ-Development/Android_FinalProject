package com.example.gamehub;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Game")
public class Game extends ParseObject {

    public Game() {

    }

    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_RATING = "rating";
    public static final String KEY_IMAGE = "image";

    public String getKeyName() {
        return getString(KEY_NAME);
    }

    public String getKeyDesc() {
        return getString(KEY_DESC);
    }

    public double getKeyRating() {
        return getDouble(KEY_RATING);
    }

    public ParseFile getKeyImage() {
        return getParseFile(KEY_IMAGE);
    }
}