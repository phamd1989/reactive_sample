package com.univtop.univtop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;

import java.util.ArrayList;
import java.util.List;

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
        private LinearLayout llTags;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
            this.tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            this.tvQuestionDesc = (TextView) itemView.findViewById(R.id.tvQuestionDesc);
            this.llTags = (LinearLayout) itemView.findViewById(R.id.llTags);
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public QuestionRecyclerAdapter(Context context) {
        this.context = context;
        this.questions = new ArrayList<>();
    }

    // inflate the view, and create the viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_item_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionRecyclerAdapter.ViewHolder holder, int position) {
        Question question = questions.get(position);
        if (holder.llTags.getChildCount() > 0) {
            holder.llTags.removeAllViews();
        }
        List<String> tags = question.getTags();
        for (int i=0; i<tags.size(); i++) {
            Button btnTag = new Button(context, null, android.R.attr.buttonStyleSmall);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            btnTag.setLayoutParams(params);
            btnTag.setText(tags.get(i));
            btnTag.setTextSize(5);
            holder.llTags.addView(btnTag);
        }
        holder.tvUsername.setText(question.getUsername());
        holder.tvQuestionDesc.setText(question.getQuestionDesc());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
