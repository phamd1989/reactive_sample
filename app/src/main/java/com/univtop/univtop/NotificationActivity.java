package com.univtop.univtop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.univtop.univtop.activities.AbstractBaseActivity;

public class NotificationActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setupToolbar();
        setupActionBar(getResources().getString(R.string.action_notification));
    }
}
