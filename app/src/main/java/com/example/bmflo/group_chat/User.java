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
    private String contactString;
    private ArrayList<Chat> chatList;
    private String chatListString;


    public User(String name, String username, String email){
        this.name = name;
        this.username = username;
        this.email = email;
        this.contacts = new ArrayList<String>();
        //contactString = contactsToString();
        this.contactString="oscar1";
    }

    public User()
    {
        contacts = new ArrayList<String>();
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

    public ArrayList<String> getContacts(){return contacts;}

    public String getContactString(){return contactString;}

    public boolean isMe(){
        return me;
    }

    public ArrayList<Chat> getChatList(){
        return chatList;
    }

    public String getChatListString() {
        return chatListString;
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

    public void setContacts(ArrayList<String> s){
        contacts = s;
    }

    public void setContactString(String s){
        contactString = s;
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
    }

    public void setChatListString(String s){chatListString = s;}

    public String contactsToString(){
        String res = "";
        for(String s: contacts){
            res+=s+",";
        }
        return res;
    }

    public void addContact(String s){
        contacts.add(s);
        contactString+=s+",";
    }

    public ArrayList<String> stringToArray(String s){
        String temp = "";
        ArrayList<String> res = new ArrayList<String>();
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)==','){
                if(temp!="initialize"){
                    res.add(temp);
                }
                temp = "";
            }
            else{
                temp+=s.charAt(i);
            }
        }
        return res;
    }

    public String chatsToString(){
        String res = "";
        for(Chat s: chatList){
            res+=s.getChatName()+",";
        }
        return res;
    }

    public void addChatViaString(String s){
        chatListString+=s+",";
    }

    public void addChat(Chat c){
        chatList.add(c);
        chatListString=chatListString+c.getChatName()+",";
    }

}
