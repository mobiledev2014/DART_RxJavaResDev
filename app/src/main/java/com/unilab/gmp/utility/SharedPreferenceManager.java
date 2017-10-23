package com.unilab.gmp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by c_jsbustamante on 10/12/2016.
 */

public class SharedPreferenceManager {

    private static SharedPreferenceManager yourPreference;
    private SharedPreferences sharedPreferences;

    private final String PREF_NAME = "UL_OJL_SHAREDPREFERENCE";
    Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceManager getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new SharedPreferenceManager(context);
        }
        return yourPreference;
    }

    public void clearAllPreferences() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        sharedPreferences.edit().clear();
        prefsEditor.commit();
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getStringData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveData(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public int getIntData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    public void saveData(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getBooleanData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

}
