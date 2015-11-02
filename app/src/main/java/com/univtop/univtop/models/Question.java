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

    public String getContent() {
        return content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getTitle() {
        return title;
    }
}
