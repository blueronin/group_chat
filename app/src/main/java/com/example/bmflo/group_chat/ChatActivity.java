package com.example.bmflo.group_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

/**
 * Created by bmflo on 8/22/2018.
 */

public class ChatActivity extends Activity {


    private Chat currentChat;
    private User currentUser;
    private String currentUsername;
    private String currentUserEmail;

    private ArrayList<Message> allMessages;
    private ArrayList<User> members;
    private String chatName;

    private EditText messageEditor;
    private ImageButton sendButton;
    private ListView messageListView;
    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private TextView chatNameView;

    RelativeLayout senderMessageLayout;
    RelativeLayout myMessageLayout;

    DatabaseReference dbRef;

    ArrayAdapter<Message> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Get Current user and convert to local user instance
        currentUsername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        Intent intent = getIntent();
        chatName = intent.getStringExtra("chatName");

        setContentView(R.layout.activity_chat);
        chatNameView = (TextView)findViewById(R.id.chat_header);
        chatNameView.setText(extractChatNameFromKey(chatName));


        linearLayout = (LinearLayout) findViewById(R.id.message_holder);
        scrollView = (ScrollView) findViewById(R.id.message_scroll_view);

        arrayAdapter = new ArrayAdapter<Message>(this, R.layout.message_item, allMessages);

        dbRef = FirebaseDatabase.getInstance().getReference().child("chats").child(chatName);

        messageEditor = (EditText) findViewById(R.id.message_edit_text);
        sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=messageEditor.getText().toString();
                User sender = new User(currentUsername, currentUsername, currentUserEmail);
                Message message = new Message(text, sender, currentUsername);
                String time = DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTimeSent()).toString();
                String messageKey = currentUsername+","+time;
                messageEditor.setText("");
                dbRef.child(messageKey).setValue(message);
            }
        });

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message currentMessage = dataSnapshot.getValue(Message.class);
                User sender = currentMessage.getSender();
                String senderUsername = currentMessage.getSenderUsename();
                String messageText = currentMessage.getText();
                long time = currentMessage.getTimeSent();
                if(senderUsername.equals(currentUsername)){
                    //from me
                    addMessage(0,0, messageText, sender.getUsername(), time);
                }
                else{
                    addMessage(1,0, messageText, sender.getUsername(), time);
                }
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
    }

    public void addMessage(int mode, int type, String message, String username, long time){

        //RelativeLayout messageLayout = (RelativeLayout) findViewById(R.id.message_layout);
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        View custLayout= inflater.inflate(R.layout.message_item, null, false);

        senderMessageLayout = (RelativeLayout) custLayout.findViewById(R.id.received_view);
        myMessageLayout = (RelativeLayout) custLayout.findViewById(R.id.sent_view);
        if(mode ==0 ){
            //from me

            myMessageLayout.setVisibility(View.VISIBLE);
            senderMessageLayout.setVisibility(View.GONE);

            TextView messageText = (TextView) custLayout.findViewById(R.id.my_message);
            TextView messageTimeText = (TextView) custLayout.findViewById(R.id.my_message_time);

            messageText.setText(message);
            messageTimeText.setText(extractTimeOnly(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", time).toString()));

            linearLayout.addView(custLayout);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
        else{
            //from other member

            myMessageLayout.setVisibility(View.GONE);
            senderMessageLayout.setVisibility(View.VISIBLE);

            TextView senderText = (TextView) custLayout.findViewById(R.id.sender_name);
            TextView messageText = (TextView) custLayout.findViewById(R.id.message);
            TextView messageTimeText = (TextView) custLayout.findViewById(R.id.sender_time);

            senderText.setText(username);
            messageText.setText(message);
            messageTimeText.setText(extractTimeOnly(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", time).toString()));

            linearLayout.addView(custLayout);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
    }

    public String extractTimeOnly(String time){
        return time.substring(12,19);
    }

    public String extractChatNameFromKey(String s){
        int i = 0;
        String name = "";
        while(s.charAt(i)!=','){
            name+=s.charAt(i);
            i+=1;
        }
        return name;
    }
}
