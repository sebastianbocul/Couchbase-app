package com.sebix.couchbase_app;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Inject
    public SharedPreferences mSharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        boolean mNightMode = mSharedPreferences.getBoolean("nightmode", false);
        if (mNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
