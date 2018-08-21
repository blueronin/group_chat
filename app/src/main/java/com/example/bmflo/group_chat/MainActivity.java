package com.example.bmflo.group_chat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    Button chatTab;
    Button contactTab;
    int mode;  //0 means we're in chat list view, 1 means we're in contact list view

    ArrayList<Chat> myChats;
    ArrayList<User> myContacts;
    ChatDBHelper chatDBHelper;
    ContactDBHelper contactDBHelper;
    ListView chatList;
    ListView contactList;

    ChatAdapter chatAdapter;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if user is signed in
        mAuth = FirebaseAuth.getInstance();
        checkSignedIn();

        setContentView(R.layout.activity_main);

        chatDBHelper = new ChatDBHelper(this);
        contactDBHelper = new ContactDBHelper(this);
        myChats = chatDBHelper.getAllChats();
        myContacts = contactDBHelper.getAllContacts();
        chatList = (ListView) findViewById(R.id.chat_list_view);
        contactList = (ListView) findViewById(R.id.contacts_list_view);

        chatAdapter = new ChatAdapter(this, myChats);
        chatList.setAdapter(chatAdapter);
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        contactAdapter = new ContactAdapter(this, myContacts);
        contactList.setAdapter(contactAdapter);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mode = 0;

        chatTab = (Button) findViewById(R.id.chats_tab_button);
        contactTab = (Button) findViewById(R.id.contacts_tab_button);

        chatTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode==0){
                    //If in chat mode do nothing
                }
                else{
                    mode = 0;
                    chatTab.setBackground(getDrawable(R.drawable.tab_border));
                    chatTab.setTextColor(R.color.colorPrimary);
                    contactTab.setBackground(getDrawable(R.drawable.tab_border_unselected));
                    contactTab.setTextColor(Color.BLACK);
                    chatList.setVisibility(View.VISIBLE);
                    contactList.setVisibility(View.GONE);
                }
            }
        });
        contactTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode==0){
                    mode = 1;
                    chatTab.setBackground(getDrawable(R.drawable.tab_border_unselected));
                    chatTab.setTextColor(Color.BLACK);
                    contactTab.setBackground(getDrawable(R.drawable.tab_border));
                    contactTab.setTextColor(R.color.colorPrimary);
                    chatList.setVisibility(View.GONE);
                    contactList.setVisibility(View.VISIBLE);
                }
                else{
                    //If in contact mode do nothing
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode==0){
                    addChat();
                }
                else{
                    addContact();
                }
            }
        });
    }

    public void checkSignedIn(){
        currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void addChat(){
        chatTab.setVisibility(View.GONE);
        contactTab.setVisibility(View.VISIBLE);

        for (int i = 0; i < contactList.getCount(); i++) {
            CheckBox checkBox = (CheckBox) contactList.getChildAt(i).findViewById(R.id.contact_select_box);
            checkBox.setVisibility(View.VISIBLE);
        }
    }

    public void addContact(){
        SaveContactDialog dialog = new SaveContactDialog();
        dialog.show(getFragmentManager(), "dialog");
    }
}
