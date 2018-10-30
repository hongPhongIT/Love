package com.pnv.matchmaking.love;

public class User {
    public String username, email, gender, birthYear, key;

    public User() {
    }

    public User(String username, String email, String gender, String birthYear, String key) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.birthYear = birthYear;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
