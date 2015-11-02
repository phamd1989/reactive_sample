package com.univtop.univtop.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.univtop.univtop.R;

public class LoginActivity extends AppCompatActivity {
    public final static int RESULT_LOGIN_CANCEL = 7034;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
