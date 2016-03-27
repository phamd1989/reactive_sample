package com.univtop.univtop.views;

import android.net.Uri;

import com.univtop.univtop.models.Question;

/**
 * Created by dungpham on 3/26/16.
 */
public interface QuestionView {
    void onFollowClick(Question question);

    void onUnfollowClick(Question question);

    void setName(String name);

    void setTimestamp(String timestamp);

    void setQuestionTitle(String questionTitle);

    void setQuestionDetail(String questionDetail);

    void setAnswer(String answer);

    void setVotes(int votes);

    void setProfilePic(Uri uri);

    void setFollowBtn(boolean isFollowed);

    void goToDetailView(Question question);
}
