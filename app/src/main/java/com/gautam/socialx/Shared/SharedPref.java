package com.gautam.socialx.Shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPreferences.Editor editor;
    Context context;

    static SharedPreferences sharedPreferences;
    String LOGGED = "LOGGED";
    static String SHARED = "SHARED";


    public static SharedPref sharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new SharedPref();

    }

    public boolean getLOGGED() {
        return sharedPreferences.getBoolean(LOGGED,false);
    }

    public void setLOGGED(boolean logged) {
        editor.putBoolean(LOGGED,logged);
        editor.commit();
    }
}
