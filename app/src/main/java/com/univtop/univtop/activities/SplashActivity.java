package com.univtop.univtop.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.univtop.univtop.R;
import com.univtop.univtop.utils.Utilities;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent next = new Intent();
                next.setClass(getApplicationContext(), HomeActivity.class);
//                if (Utilities.getApiKey(SplashActivity.this) != null) {
//                    next.setClass(getApplicationContext(), HomeActivity.class);
//                } else {
//                    next.setClass(getApplicationContext(), LoginActivity.class);
//                }
                startActivity(next);
                finish();
            }
        }, 2000);
    }
}
