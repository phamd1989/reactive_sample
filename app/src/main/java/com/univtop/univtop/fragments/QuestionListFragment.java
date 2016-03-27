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
import android.widget.ProgressBar;

import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.adapters.QuestionListAdapter;
import com.univtop.univtop.manager.PresenterManager;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.presenters.QuestionListPresenter;
import com.univtop.univtop.views.QuestionListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dungpham on 3/26/16.
 */
public class QuestionListFragment extends Fragment implements QuestionListView {
    @Bind(R.id.newsfeed_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.newsfeed_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.empty_swiperefreshlayout)
    SwipeRefreshLayout mEmptySRL;
    @Bind(R.id.loadQuestions_button)
    Button mLoadQuestions;
    @Bind(R.id.loading_row)
    ProgressBar progressBar;
    @Bind(R.id.question_list)
    View questionList;

    private QuestionListAdapter mAdapter;

    private QuestionListPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.newsfeed_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            mPresenter = new QuestionListPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

//        mSwipeRefreshLayout.setColorSchemeResources(Utilities.getRefreshColorArray());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.setModel(null);
                mPresenter.bindView(QuestionListFragment.this);
            }
        });
        mEmptySRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.setModel(null);
                mPresenter.bindView(QuestionListFragment.this);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new QuestionListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        questionList.setVisibility(View.GONE);
    }

    @Override
    public void showQuestions(List<Question> questions) {
        progressBar.setVisibility(View.GONE);
        questionList.setVisibility(View.VISIBLE);
        mAdapter.clearAndAddAll(questions);
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
    public void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unbindView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    public void scrollToFirst() {
        mRecyclerView.scrollToPosition(0);
    }
}
