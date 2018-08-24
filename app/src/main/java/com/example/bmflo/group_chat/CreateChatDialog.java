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
import android.view.inputmethod.InputMethodManager;
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
    private ArrayList<User> membersInfo;
    private User currentUser;
    private TextView result;
    private RelativeLayout resultLayout;

    String chatName;

    String currentUserName;
    String members;
    String searchedUser;
    String email;

    DatabaseReference usersRef;
    DatabaseReference chatsRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_chat, null);

        currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

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
        addButton = (ImageButton) view.findViewById(R.id.addMemberButton);
        result = (TextView) view.findViewById(R.id.result);
        resultLayout = (RelativeLayout) view.findViewById(R.id.resultEntry);
        membersInfo = new ArrayList<User>();
        members = "";

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


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultLayout.setVisibility(View.VISIBLE);
                result.setText(searchEdit.getText().toString());
                searchedUser = searchEdit.getText().toString();
                searchEdit.setText("");
                //addMemberToChat();
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                members=members+searchedUser+",";
                String message = "Added "+searchedUser+"!!";
                result.setText(message);
            }
        });



        builder.setTitle("Create Chat").setView(view).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //members.add(currentUser.getUsername());
                //membersInfo.add(currentUser);
                Chat chat = new Chat();
                members = members + currentUserName + ",";
                chatName = chatNameEdit.getText().toString();
                chatName=chatName+","+members;

                //chat name is set chat name plus the usernames for all members
                chat.setChatName(chatName);
                //chat.setMembers(members);

                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatName", chatName);
                startActivity(intent);
            }
        });



        return builder.create();
    }



    public void addMemberToChat(){
        String username = searchEdit.getText().toString();
        searchEdit.setText("");
        searchedUser = username;

    }
}
