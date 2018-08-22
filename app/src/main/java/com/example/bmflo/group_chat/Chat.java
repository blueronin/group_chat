package com.example.bmflo.group_chat;

import java.util.ArrayList;

/**
 * Created by bmflo on 8/19/2018.
 */

public class Chat {

    private String chatName;
    private ArrayList<String> members;
    private ArrayList<Message> messages;

    public Chat(){
        chatName = "Untitled";
        members = new ArrayList<String>();
        messages = new ArrayList<Message>();
    }

    public String getChatName(){
        return chatName;
    }

    public ArrayList<String> getMembers(){
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setChatName(String name){
        chatName = name;
    }

    public void setMembers(ArrayList<String> arrayList){
        members = arrayList;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addMember(String user){
        members.add(user);
    }

    public String membersToString(){
        String result = "";
        for(String s:members){
            result += s + ",";
        }
        return result;
    }

    public ArrayList<String> stringToMembers(String s){
        String currentMember = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)==','){
                arrayList.add(currentMember);
                currentMember="";
            }
            else{
                currentMember+=s.charAt(i);
            }
        }
        return arrayList;
    }
}
