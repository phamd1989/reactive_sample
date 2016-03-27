package com.univtop.univtop.presenters;

import android.net.Uri;

import com.univtop.univtop.models.Question;
import com.univtop.univtop.views.QuestionView;

/**
 * Created by dungpham on 3/26/16.
 */
public class QuestionPresenter extends BasePresenter<Question, QuestionView> {
    @Override
    protected void updateView() {
        view().setAnswer(Integer.toString(model.getAnswers()) + " Answers");
        view().setName(model.getUser().getUsername());
        view().setTimestamp(model.getTimestamp());
        view().setQuestionTitle(model.getQuestionTitle());
        view().setQuestionDetail(model.getQuestionDetails());
        view().setVotes(model.getVotes());
        view().setProfilePic(Uri.parse(model.getUser().getAvatar()));
        boolean isFollowed = model.is_followed();
        view().setFollowBtn(isFollowed);
    }

    public void onFollowBtnClicked() {
        if (setupDone()) {
            model.setIs_followed(true);
            updateView();
        }
    }

    public void onUnfollowBtnClicked() {
        if (setupDone()) {
            model.setIs_followed(false);
            updateView();
        }
    }

    public void onQuestionClicked() {

    }
}
