package com.example.bmflo.group_chat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    User currentUserLocal;

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

    DatabaseReference chatsRef;

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if user is signed in
        mAuth = FirebaseAuth.getInstance();
        checkSignedIn();

        setContentView(R.layout.activity_main);


        //Set up chat list for view

        chatList = (ListView) findViewById(R.id.chat_list_view);
        contactList = (ListView) findViewById(R.id.contacts_list_view);
        contactList.setVisibility(View.GONE);

        myChats = new ArrayList<Chat>();

        chatsRef = FirebaseDatabase.getInstance().getReference().child("chats");
        chatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat currentChat = dataSnapshot.getValue(Chat.class);
                currentChat.setChatName(dataSnapshot.getKey());
                myChats.add(currentChat);

                chatAdapter = new ChatAdapter(MainActivity.this, myChats);
                chatList.setAdapter(chatAdapter);
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



        //chat list done



        myContacts=new ArrayList<User>();

        //Get Current user and convert to local user instance
        //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //got user


        contactDBHelper = new ContactDBHelper(this);
        myContacts = contactDBHelper.getAllContacts();
        //myContacts.add(new User(currentUser.getDisplayName(), currentUser.getDisplayName(), currentUser.getEmail()));

        //myContacts = extractContactsFromStringList(currentUserLocal.stringToContacts(currentUserLocal.getContactString()));
        //showCurrentUser();



        chatDBHelper = new ChatDBHelper(this);
        //contactDBHelper = new ContactDBHelper(this);
        //myChats = chatDBHelper.getAllChats();
        //myContacts = contactDBHelper.getAllContacts();
        //chatList = (ListView) findViewById(R.id.chat_list_view);
        //contactList = (ListView) findViewById(R.id.contacts_list_view);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = myChats.get(position).getChatName();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("chatName", name);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
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
        //Test with default chat to darrian

        CreateChatDialog dialog = new CreateChatDialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    public void addContact(){
        SaveContactDialog dialog = new SaveContactDialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    public ArrayList<User> extractContactsFromStringList(ArrayList<String> contactStringList){
        final ArrayList<User> result = new ArrayList<User>();
        for(String s: contactStringList){
            Query query1 = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(s);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            User currentContact = snapshot.getValue(User.class);
                            result.add(currentContact);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return result;
    }

    public void showCurrentUser(){
        String check = currentUserLocal.getUsername();
    }
}
