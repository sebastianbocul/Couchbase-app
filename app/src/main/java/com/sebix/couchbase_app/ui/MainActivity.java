package com.sebix.couchbase_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sebix.couchbase_app.R;
import com.sebix.couchbase_app.ui.MainFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMainFragment();
    }

    private void openMainFragment(){
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, mainFragment)
                .commit();
    }

}