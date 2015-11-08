package com.univtop.univtop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by haipn on 11/5/15.
 */
public class User implements Serializable{
    String first_name;

    String last_name;

    String username;

    String resource_uri;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return resource_uri;
    }

    public void setEmail(String email) {
        this.resource_uri = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
