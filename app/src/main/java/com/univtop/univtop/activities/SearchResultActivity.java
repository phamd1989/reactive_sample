package com.univtop.univtop.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.adapters.NewsFeedAdapter;
import com.univtop.univtop.adapters.PageableListAdapter;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.utils.DebugLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchResultActivity extends AbstractBaseActivity implements PageableListAdapter.PageableListListener {

    @Bind(R.id.searchresult_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.searchresult_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsFeedAdapter mAdapter;
    private String mQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        setupToolbar();
        setupActionBar("");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        if (mAdapter == null) {
            mAdapter = new NewsFeedAdapter(this);
            mAdapter.setLoading(true);
            mAdapter.setListener(this);
        }
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DebugLog.d("query:" + query);
            doMySearch(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        DebugLog.d("query:" + query);
        doMySearch(query);
        super.onNewIntent(intent);
    }

    private void doMySearch(String query) {
        mQuery = query;
        refreshContent();
    }

    public void refreshContent() {
        mAdapter.emptyDataOnRefresh();
        DebugLog.d(" refresh content");
        APIService.getInstance().getQueryQuestions(10, 0, mQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAdapter.getSubscriber());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem search = menu.findItem(R.id.action_search);
//        search.expandActionView();
        SearchView searchView = (SearchView) search.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        mAdapter.removeListener();
        super.onDestroy();
    }

    @Override
    public void refreshFinished() {
        UnivtopApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 200);
    }

    @Override
    public void empty() {

    }

    @Override
    public void notEmpty() {

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

}
