package com.example.bmflo.group_chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bmflo on 8/22/2018.
 */

public class CreateChatDialog extends DialogFragment {

    private EditText chatNameEdit;
    private EditText searchEdit;
    private ImageButton searchButton;
    private ImageButton addButton;
    private ArrayList<String> members;
    private ArrayList<User> membersInfo;
    private User currentUser;
    private TextView result;
    private RelativeLayout resultLayout;

    String chatName;

    DatabaseReference usersRef;
    DatabaseReference chatsRef;
    FirebaseAuth auth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_chat, null);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        currentUser = snapshot.getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chatNameEdit = (EditText) view.findViewById(R.id.name_edit_view);
        searchEdit = (EditText) view.findViewById(R.id.search_view);
        searchButton = (ImageButton) view.findViewById(R.id.search_button);
        //addButton = (ImageButton) view.findViewById(R.id.addMemberButton);
        result = (TextView) view.findViewById(R.id.result);
        resultLayout = (RelativeLayout) view.findViewById(R.id.resultEntry);
        members = new ArrayList<String>();
        membersInfo = new ArrayList<User>();

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        chatsRef = FirebaseDatabase.getInstance().getReference().child("chats");

        chatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemberToChat();
            }
        });
        */
        /*

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Just for view purposes
                String message = "Added user!";
                result.setText(message);
            }
        });

        */

        builder.setTitle("Create Chat").setView(view).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                members.add(currentUser.getUsername());
                membersInfo.add(currentUser);
                Chat chat = new Chat();
                chatName = chatNameEdit.getText().toString();
                chat.setChatName(chatName);
                chat.setMembers(members);

                //chatsRef.child(chatName).setValue(chat);

                /*
                for(User u:membersInfo){
                    u.addChatViaString(chatName);
                    String newChatString = u.getChatListString();

                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put(u.getUsername()+"/chatListString", newChatString);

                    usersRef.updateChildren(userUpdates);
                }
                */

                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatName", chatName);
                //intent.putExtra("members",members);
                startActivity(intent);
            }
        });

        return builder.create();
    }



    public void addMemberToChat(){
        String username = searchEdit.getText().toString();
        searchEdit.setText("");
        Query query = usersRef.orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);

                        resultLayout.setVisibility(View.VISIBLE);
                        String userInfo = (user.getUsername()+"\n"+user.getName()+"\n"+user.getEmail());
                        result.setText(userInfo);

                        user.addChatViaString(chatName);
                        String newChatString = user.getChatListString();

                        Map<String, Object> userUpdates = new HashMap<>();
                        userUpdates.put(user.getUsername()+"/chatListString", newChatString);

                        usersRef.updateChildren(userUpdates);


                        //Makes add Button irrelevant, just for view purposes
                        //members.add(user.getUsername());
                        //membersInfo.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
