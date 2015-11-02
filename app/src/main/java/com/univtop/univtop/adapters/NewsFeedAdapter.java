package com.univtop.univtop.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dungpham on 11/1/15.
 */
public class NewsFeedAdapter extends PageableListAdapter<Question> {

    public NewsFeedAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @Bind(R.id.question_details)
        TextView questionDetails;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            rootView = v;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        Question question = getItemAtPosition(position);
        String questionDetail = question.getTitle();
        final ViewHolder vh = (ViewHolder) holder;
        vh.questionDetails.setText(questionDetail);
    }

    @Override
    public void fetchPage(String url) {
        super.fetchPage(url);
    }
}
