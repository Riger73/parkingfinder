package com.pp1.parkingfinder;

import android.app.Application;

import com.pp1.parkingfinder.model.User;
import com.pp1.parkingfinder.model.Leaser;

public class UserClient extends Application {

    private User user = null;

    private Leaser leaser = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}