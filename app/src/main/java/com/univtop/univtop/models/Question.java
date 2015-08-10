package com.univtop.univtop.models;

import java.util.List;

/**
 * Created by duncapham on 8/9/15.
 */
public class Question {
    private String profilePicUrl;
    private String username;
    private String qId;
    private List<String> tags;
    private String questionDesc;

    // to create fake data
    public Question(String username, String questionDesc) {
        this.username = username;
        this.questionDesc = questionDesc;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }
}
