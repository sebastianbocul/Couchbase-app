package com.sebix.couchbase_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sebix.couchbase_app.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView mLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLogoImageView = findViewById(R.id.image_logo);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
        mLogoImageView.startAnimation(scaleAnim);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLogoImageView.clearAnimation();
                mLogoImageView.setVisibility(View.GONE);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, getResources().getInteger(R.integer.delay));
    }
}