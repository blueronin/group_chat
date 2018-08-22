package com.example.bmflo.group_chat;

import java.util.Date;

/**
 * Created by bmflo on 8/22/2018.
 */

public class Message {

    private String text;
    private int type; //text or video
    private User sender;
    private String senderUsername;
    private long timeSent;

    public Message(String text, User sender, String senderUsename){
        this.text = text;
        this.type = 0;
        this.sender = sender;
        this.senderUsername = sender.getUsername();
        this.timeSent = new Date().getTime();
    }

    public Message(){
        this.text="";
        this.type=0;
        this.sender = new User();
        this.senderUsername="Unknown";
        this.timeSent = new Date().getTime();
    }

    public String getText(){
        return text;
    }

    public int getType(){
        return type;
    }

    public User getSender() {
        return sender;
    }

    public String getSenderUsename() {
        return senderUsername;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setSenderUsename(String senderUsename) {
        this.senderUsername = senderUsename;
    }

    public void setTimeSent(long timeSent) {
        this.timeSent = timeSent;
    }
}
