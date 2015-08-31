package com.hackathon.sequoia.sequoiahackathon.global;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manishkumar on 29/08/15.
 */
public class AppPreference {

    private static final String PREFS_NAME = "app_prefs";

    private static final String LOGGED_IN = "logged_in";
    private static final String USER_ID = "user_id";

    private static AppPreference sInstance;

    private SharedPreferences mPrefs;

    public static AppPreference getInstance(Context context) {
        if (sInstance == null) {
            sInstance =  new AppPreference(context);
        }
        return sInstance;
    }

    private AppPreference(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean isUserLoggedin() {
        return mPrefs.getBoolean(LOGGED_IN, false);
    }

    public void setUserLoginStatus(boolean loggedIn) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(LOGGED_IN, loggedIn);
        editor.commit();
    }

    public void setUserLoginId(int id) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(USER_ID, id);
        editor.commit();
    }

    public int getUserId() {
        return mPrefs.getInt(USER_ID, -1);
    }
}
