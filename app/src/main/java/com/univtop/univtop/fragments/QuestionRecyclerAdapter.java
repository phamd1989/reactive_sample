package com.univtop.univtop.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;

import java.util.ArrayList;

/**
 * Created by duncapham on 8/9/15.
 */
public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {

    private ArrayList<Question> questions;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUser;
        private TextView tvUsername;
        private TextView tvQuestionDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
            this.tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            this.tvQuestionDesc = (TextView) itemView.findViewById(R.id.tvQuestionDesc);
        }
    }

    public QuestionRecyclerAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    // inflate the view, and create the viewholder
    @Override
    public QuestionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_item_question, parent, false);
        return new QuestionRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionRecyclerAdapter.ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.tvUsername.setText(question.getUsername());
        holder.tvQuestionDesc.setText(question.getQuestionDesc());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
