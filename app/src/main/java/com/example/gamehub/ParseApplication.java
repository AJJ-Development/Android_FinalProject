package com.example.gamehub;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(LikedGame.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("BPU2bc1tb51POhBMYhTCBATyJwik9Xj08EGLKAmA")
                .clientKey("1ICTCnMK88KW0ShWJ2wtE7opuZbbV0M3c5W5Zrvo")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
