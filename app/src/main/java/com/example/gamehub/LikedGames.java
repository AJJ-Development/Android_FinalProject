package com.example.gamehub;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("LikedGames")
public class LikedGames extends ParseObject {
    public static final String KEY_GAME_ID = "gameId";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_USER = "user";

    public String getGameId() {
        return getString(KEY_GAME_ID);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setGameId(String gameId) {
        put(KEY_GAME_ID, gameId);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
