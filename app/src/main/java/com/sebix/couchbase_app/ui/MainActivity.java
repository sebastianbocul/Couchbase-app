package com.sebix.couchbase_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sebix.couchbase_app.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ImageView mLogoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMainFragment();
    }

    private void openMainFragment(){
        mLogoImageView = findViewById(R.id.image_logo);
        Animation scaleAnim = AnimationUtils.loadAnimation(this,R.anim.scale);
        mLogoImageView.startAnimation(scaleAnim);
        MainFragment mainFragment = new MainFragment();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLogoImageView.clearAnimation();
                mLogoImageView.setVisibility(View.GONE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, mainFragment)
                        .commit();
            }
        }, 3000);
    }

}