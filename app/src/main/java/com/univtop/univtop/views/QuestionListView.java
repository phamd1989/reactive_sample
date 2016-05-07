package com.univtop.univtop.views;

import com.univtop.univtop.models.Question;

import java.util.List;

/**
 * Created by dungpham on 3/26/16.
 */
public interface QuestionListView {
    void showEmpty();

    void showLoading();

    void showQuestions(List<Question> questions, String nextPageUrl);

    void refreshFinished();
}
