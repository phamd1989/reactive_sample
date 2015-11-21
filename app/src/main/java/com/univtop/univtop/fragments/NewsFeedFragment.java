package com.univtop.univtop.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.activities.QuestionDetailActivity;
import com.univtop.univtop.adapters.NewsFeedAdapter;
import com.univtop.univtop.adapters.PageableListAdapter;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.services.APIService;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dungpham on 11/1/15.
 */
public class NewsFeedFragment extends Fragment implements PageableListAdapter.PageableListListener, NewsFeedAdapter.ClickQuestionDetailListener{

    @Bind(R.id.newsfeed_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.newsfeed_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.empty_swiperefreshlayout)
    SwipeRefreshLayout mEmptySRL;
    @Bind(R.id.loadQuestions_button)
    Button mLoadQuestions;

    private NewsFeedAdapter mAdapter;
    private final int QUESTION_DETAIL_CODE = 101;
    public static final String QUESTION = "question";
    public static final String POSITION = "position";
    public static final String FOLLOWING = "isFollowing";

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
            mAdapter.setQuestionDetailListener(this);
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
        UnivtopApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        }, 100);    }

    @Override
    public void notEmpty() {
        // TODO: hide empty screen content
    }

    public void scrollToFirst() {
        mRecyclerView.scrollToPosition(0);
    }


    @Override
    public void launch(Question question, int pos) {
        startActivityForResult(QuestionDetailActivity.getLaunchIntent(getActivity(), question, pos), QUESTION_DETAIL_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUESTION_DETAIL_CODE && resultCode == Activity.RESULT_OK) {
            int pos = data.getIntExtra(POSITION, -1);
            boolean isFollowing = data.getBooleanExtra(FOLLOWING, false);
            if (pos >= 0) {
                mAdapter.refreshAtPos(pos, isFollowing);
            }
        }
    }
}
