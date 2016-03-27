package com.univtop.univtop.views;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.presenters.QuestionPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dungpham on 3/26/16.
 */
public class QuestionViewHolder extends MvpViewHolder<QuestionPresenter> implements QuestionView{
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

    public QuestionViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFollowBtnClicked();
            }
        });
        unfollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUnfollowBtnClicked();
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onQuestionClicked();
            }
        });
    }

    @Override
    public void onFollowClick(Question question) {

    }

    @Override
    public void onUnfollowClick(Question question) {

    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setTimestamp(String timestamp) {
        this.timestamp.setText(timestamp);
    }

    @Override
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public void setQuestionDetail(String questionDetail) {
        this.questionDetails.setText(questionDetail);
    }

    @Override
    public void setAnswer(String answer) {
        this.answers.setText(answer);
    }

    @Override
    public void setVotes(int votes) {
        if (votes > 0) this.upvotes.setText(Integer.toString(votes));
    }

    @Override
    public void setProfilePic(Uri uri) {
        this.profilePic.setImageURI(uri);
    }

    @Override
    public void setFollowBtn(boolean isFollowed) {
        if (isFollowed) {
            this.followBtn.setVisibility(View.GONE);
            this.unfollowBtn.setVisibility(View.VISIBLE);
        } else {
            this.followBtn.setVisibility(View.VISIBLE);
            this.unfollowBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToDetailView(Question question) {

    }
}
