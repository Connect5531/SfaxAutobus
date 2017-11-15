package com.houcemhariz.sfaxautobus.persistence.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LENOVO on 19/10/2017.
 */

public class AppPreferences {

    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String first_run = "first_run_prefs";

    public AppPreferences(Context context){
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public boolean getFirst_run() {
        return appSharedPrefs.getBoolean(first_run, false);
    }

    public void setFirst_run(boolean _first_run) {
        prefsEditor.putBoolean(first_run, _first_run).commit();
    }
}
