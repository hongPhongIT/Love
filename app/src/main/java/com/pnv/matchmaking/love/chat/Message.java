package com.pnv.matchmaking.love.chat;

import java.util.Date;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String messageText;
    private String messageUser;
    private long messageTime;
    public String name;
    public String email;


    public Message(String messageText, String messageUser, String name, String email) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.name = name;
        this.email = email;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public Message() {

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
