package com.pnv.matchmaking.love.profile;

public class User {
    public String username, email, gender, birthYear;

    public User() {
    }

    public User(String username, String email, String gender, String birthYear) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.birthYear = birthYear;
    }
}
