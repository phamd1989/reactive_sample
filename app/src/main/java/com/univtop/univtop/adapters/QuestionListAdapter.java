package com.univtop.univtop.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.presenters.QuestionListPresenter;
import com.univtop.univtop.presenters.QuestionPresenter;
import com.univtop.univtop.views.QuestionView;
import com.univtop.univtop.views.QuestionViewHolder;

/**
 * Created by dungpham on 3/26/16.
 */
public class QuestionListAdapter extends MvpRecyclerListAdapter<Question, QuestionPresenter, QuestionViewHolder>{
    @NonNull
    @Override
    protected QuestionPresenter createPresenter(@NonNull Question model) {
        QuestionPresenter presenter = new QuestionPresenter();
        presenter.setModel(model);
        return presenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Question model) {
        return model.getId();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row, parent, false);
        QuestionViewHolder viewHolder = new QuestionViewHolder(v);
        return viewHolder;
    }
}
