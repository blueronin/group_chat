package com.example.bmflo.group_chat;

import java.util.ArrayList;

/**
 * Created by bmflo on 8/18/2018.
 *
 * Stores user information for contacts and current User
 */

public class User {

    private String name;
    private String username;
    private String email;
    private boolean me;

    //contacts saved in DB
    private ArrayList<String> contacts; //usernames for duplicate prevention

    public User(String name, String username, String email){
        this.name = name;
        this.username = username;
        this.email = email;
        this.me = false;
    }

    public String getName(){
        return  name;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public boolean isMe(){
        return me;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setMe(boolean me){
        this.me = me;
    }


}
