package com.univtop.univtop.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duncapham on 8/9/15.
 */
public class Question implements Serializable{
    private String content;
    private String create_date;
    private String title;
    private int total_answer;
    private int total_vote;
    private User user_post;

    public String getQuestionDetails() {
        return content;
    }

    public String getTimestamp() { return create_date; }

    public String getQuestionTitle() {
        return title;
    }

    public int getVotes() {return total_vote;}

    public int getAnswers() {return total_answer;}

    public User getUser() {return user_post;}
}
