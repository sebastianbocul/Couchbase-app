package com.sebix.couchbase_app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sebix.couchbase_app.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragment mainFragment = new MainFragment();
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, mainFragment)
                .commit();
    }
}