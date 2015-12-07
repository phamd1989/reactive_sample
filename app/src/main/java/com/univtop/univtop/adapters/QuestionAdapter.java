package com.univtop.univtop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.views.VHQuestion;

import butterknife.Bind;

/**
 * Created by dungpham on 11/25/15.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Question mQuestion;
    private AnswerAdapter mAnswerAdapter;

    private final int NUM_EXTRA_VIEWS = 1;

    public QuestionAdapter(Question question) {
        super();
        mQuestion = question;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_detail, parent, false);
        VHQuestion viewHolder = new VHQuestion(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VHQuestion questionVH = (VHQuestion) holder;
        VHQuestion.setupView(mContext, mQuestion, questionVH);
    }

    @Override
    public int getItemCount() {
//        if (mAnswerAdapter == null) return NUM_EXTRA_VIEWS + 1;
//        return mAnswerAdapter.getItemCount() > 0 ?
//                mAnswerAdapter.getItemCount() + NUM_EXTRA_VIEWS : NUM_EXTRA_VIEWS + 1;
        return 1;
    }
}
