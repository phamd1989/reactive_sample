package com.univtop.univtop.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.univtop.univtop.NotificationActivity;
import com.univtop.univtop.R;
import com.univtop.univtop.utils.DebugLog;

import butterknife.ButterKnife;

public class SearchActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem search = menu.findItem(R.id.action_search);
//        search.expandActionView();
        SearchView searchView = (SearchView) search.getActionView();
        // Assumes current activity is the searchable activity
        ComponentName cn = new ComponentName(this, SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onSearchRequested() {

        return super.onSearchRequested();
    }

    @Override
    protected HomeButtonType getHomeButtonType() {
        return HomeButtonType.BACK;
    }
}
