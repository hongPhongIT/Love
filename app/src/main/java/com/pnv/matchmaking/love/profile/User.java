package com.pnv.matchmaking.love.profile;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String username, email, gender, birthYear, intro;

    public User() {
    }


    public User(String username, String email, String gender, String birthYear) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.birthYear = birthYear;
    }

    public User(String userKey, String username, String intro) {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);
        result.put("gender", gender);
        result.put("birthYear", birthYear);
        result.put("intro", intro);

        return result;
    }
}
