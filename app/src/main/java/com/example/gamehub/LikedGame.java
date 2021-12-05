package com.example.gamehub;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("LikedGame")
public class LikedGame extends ParseObject {

    public static final String KEY_GAME_ID = "gameId";
    public static final String KEY_USER = "user";

    public String getGameId() {
        return KEY_GAME_ID;
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
}
