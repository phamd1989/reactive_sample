package com.univtop.univtop.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.univtop.univtop.R;
import com.univtop.univtop.fragments.NewsFeedFragment;

import butterknife.ButterKnife;

public class HomeActivity extends AbstractBaseActivity {
    private NewsFeedFragment mFragment;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        setupToolbar();
        setupActionBar(null);

        mFragment = new NewsFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
    }

    public static void getLaunchIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
