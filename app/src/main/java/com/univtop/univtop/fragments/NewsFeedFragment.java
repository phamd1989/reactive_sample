package com.univtop.univtop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.adapters.NewsFeedAdapter;
import com.univtop.univtop.adapters.PageableListAdapter;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dungpham on 11/1/15.
 */
public class NewsFeedFragment extends Fragment implements PageableListAdapter.PageableListListener{

    @Bind(R.id.newsfeed_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.newsfeed_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.empty_swiperefreshlayout)
    SwipeRefreshLayout mEmptySRL;
    @Bind(R.id.loadQuestions_button)
    Button mLoadQuestions;

    private NewsFeedAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.newsfeed_fragment, container, false);
        ButterKnife.bind(this, rootView);

//        mSwipeRefreshLayout.setColorSchemeResources(Utilities.getRefreshColorArray());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        mEmptySRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        if (mAdapter == null) {
            mAdapter = new NewsFeedAdapter(getActivity());
            mAdapter.setLoading(true);
            mAdapter.setListener(this);
        }
        mRecyclerView.setAdapter(mAdapter);
        refreshContent();
        return rootView;
    }

    public void refreshContent() {
        mAdapter.emptyDataOnRefresh();
        APIService.getInstance().getPublicQuestions(10, 0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAdapter.getSubscriber());
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        mAdapter.removeListener();
    }

    @Override
    public void refreshFinished() {
        UnivtopApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 200);
        UnivtopApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmptySRL.setRefreshing(false);
            }
        }, 200);
    }

    @Override
    public void empty() {
        // TODO: show empty screen content
    }

    @Override
    public void notEmpty() {
        // TODO: hide empty screen content
    }

    public void scrollToFirst() {
        mRecyclerView.scrollToPosition(0);
    }
}
