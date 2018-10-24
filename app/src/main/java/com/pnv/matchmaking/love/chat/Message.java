package com.pnv.matchmaking.love.chat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String messageText;
    private String messageUser;
    private long messageTime;


    public Message(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

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

    public String getMessageTime() {
        return String.valueOf(messageTime);
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("messageText", messageText);
        result.put("messageUser", messageUser);
        result.put("messageTime", messageTime);
        return result;
    }
}
