package com.pnv.matchmaking.love;

public class Friend {

    private String friendName;

    // Image name (Without extension)
    private String avatarName;
    private String quote;

    public Friend(String friendName, String avatarName, String quote) {
        this.friendName= friendName;
        this.avatarName= avatarName;
        this.quote= quote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getAvatarName() {
        return avatarName;
    }
                                                                                                                                                                                                                                                  
    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    @Override
    public String toString()  {
        return this.friendName+" (Quote: "+ this.quote+")";
    }
}
