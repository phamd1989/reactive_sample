package com.univtop.univtop.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haipn on 11/5/15.
 */
public class User {
    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("username")
    String userName;

    String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
