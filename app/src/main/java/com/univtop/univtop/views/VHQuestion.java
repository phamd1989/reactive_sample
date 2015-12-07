package com.univtop.univtop.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dungpham on 11/25/15.
 */
public class VHQuestion extends RecyclerView.ViewHolder{
    private View itemView;

    @Bind(R.id.profile_pic_iv)
    public SimpleDraweeView profilePic;
    @Bind(R.id.name_tv)
    public TextView name;
    @Bind(R.id.timestamp_tv)
    public TextView timestamp;
    @Bind(R.id.question_title_tv)
    public TextView questionTitle;
    @Bind(R.id.question_details_tv)
    public TextView questionDetails;
    @Bind(R.id.upvote_btn)
    public View upvote;
    @Bind(R.id.answer_tv)
    public TextView answers;
    @Bind(R.id.follow_btn)
    public View followBtn;
    @Bind(R.id.unfollow_btn)
    public View unfollowBtn;
    @Bind(R.id.upvotes_tv)
    public TextView upvotes;

    public static VHQuestion mQuestionVH;

    public VHQuestion(View view) {
        super(view);
        this.itemView = view;
        setupViewHolder();
    }

    private void setupViewHolder() {
        ButterKnife.bind(this, itemView);
    }

    public static void setupView(final Context context, final Question question, final VHQuestion questionVH) {

        final boolean isFollowed = question.is_followed();
        mQuestionVH = questionVH;

        questionVH.name.setText(question.getUser().getUsername());
        questionVH.timestamp.setText(question.getTimestamp());
        questionVH.questionTitle.setText(question.getQuestionTitle());
        questionVH.questionDetails.setText(question.getQuestionDetails());
        questionVH.answers.setText(Integer.toString(question.getAnswers()) + " Answers");
        if (question.getVotes() > 0) {
            questionVH.upvotes.setText(Integer.toString(question.getVotes()));
        }
        questionVH.profilePic.setImageURI(Uri.parse(question.getUser().getAvatar()));
        if (isFollowed) {
            questionVH.followBtn.setVisibility(View.GONE);
            questionVH.unfollowBtn.setVisibility(View.VISIBLE);
            questionVH.unfollowBtn.setOnClickListener(getUnfollowClickListener(question));
        } else {
            questionVH.followBtn.setVisibility(View.VISIBLE);
            questionVH.unfollowBtn.setVisibility(View.GONE);
            questionVH.followBtn.setOnClickListener(getFollowClickListener(question));
        }
    }

    static View.OnClickListener getFollowClickListener(final Question question) {
        final boolean isFollowed = question.is_followed();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionVH.followBtn.setVisibility(View.GONE);
                mQuestionVH.unfollowBtn.setVisibility(View.VISIBLE);
                mQuestionVH.unfollowBtn.setOnClickListener(getUnfollowClickListener(question));
                question.setIs_followed(!isFollowed);
            }
        };
    }

    static View.OnClickListener getUnfollowClickListener(final Question question) {
        final boolean isFollowed = question.is_followed();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionVH.followBtn.setVisibility(View.VISIBLE);
                mQuestionVH.followBtn.setOnClickListener(getFollowClickListener(question));
                mQuestionVH.unfollowBtn.setVisibility(View.GONE);
                question.setIs_followed(!isFollowed);
            }
        };
    }
}
