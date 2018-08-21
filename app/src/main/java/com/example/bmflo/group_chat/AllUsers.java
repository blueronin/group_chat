package com.example.bmflo.group_chat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
//import com.google.api.gax.paging.Page;

/**
 * Created by bmflo on 8/19/2018.
 */

public class AllUsers {
    Map<String, User> userMap;
    //String is username


    public void AllUsers(){
        userMap=new HashMap<String, User>();

        /*
        ListUsersPage listUsersPage = new FirebaseAuth.getInstance().listUsers(null);
        page = FirebaseAuth.getInstance().listUsers(null);
        for (ExportedUserRecord user : page.iterateAll()) {
            System.out.println("User: " + user.getUid());
        }
        */
    }
}
