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

//import com.google.android.gms.auth.api.Auth;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.LowLevelHttpRequest;
//import com.google.api.client.json.JsonGenerator;
//import com.google.api.client.json.JsonParser;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.ResourceId;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//mport com.google.api.services.youtube.model.Thumbnail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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



    //Youtube
    private static final String PROPS = "youtube.properties";
    //private static YouTube youtube;
    private static final long NUM_RESULTS = 1;

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
                String messageKey = time+","+currentUsername;
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


        //Youtube


        //Causes crash
        /*
        Properties properties = new Properties();
        try {
            InputStream in = YouTube.Search.class.getResourceAsStream("/" + PROPS);
            properties.load(in);

        } catch (IOException e) {
            System.exit(1);
        }
        */
        /*
        try {
            //Maybe problem here because it is used for api request
            youtube = new YouTube.Builder(new HttpTransport() {
                @Override
                protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                    return null;
                }
            }, new JsonFactory() {
                @Override
                public JsonParser createJsonParser(InputStream in) throws IOException {
                    return null;
                }

                @Override
                public JsonParser createJsonParser(InputStream in, Charset charset) throws IOException {
                    return null;
                }

                @Override
                public JsonParser createJsonParser(String value) throws IOException {
                    return null;
                }

                @Override
                public JsonParser createJsonParser(Reader reader) throws IOException {
                    return null;
                }

                @Override
                public JsonGenerator createJsonGenerator(OutputStream out, Charset enc) throws IOException {
                    return null;
                }

                @Override
                public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
                    return null;
                }
            }, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {

                }
            }).setApplicationName("group_chat").build();

        }catch (Throwable t) {
            t.printStackTrace();
        }
        */

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


    //Youtube
    public void searchYoutube(View view){
        String s = messageEditor.getText().toString();
        if(s.startsWith("/youtube ")){

            /*
            try {
                String queryTerm = s.substring(9);

                YouTube.Search.List search = youtube.search().list("id,snippet");

                //String apiKey = properties.getProperty("youtube.apikey");
                //search.setKey(apiKey);
                search.setKey("AIzaSyCSSrLaUZ6wvPZwYXAc_V5c2VAfqgF1ocM");
                search.setQ(queryTerm);

                search.setType("video");

                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(NUM_RESULTS);

                // Error here getting the api even with manual api entry to search
                SearchListResponse searchResponse = search.execute();
                List<SearchResult> searchResultList = searchResponse.getItems();
                if (searchResultList != null) {
                    getResult(searchResultList.iterator(), queryTerm);
                }
            }catch (GoogleJsonResponseException e) {
            } catch (IOException e) {
            }
            */
            messageEditor.setText("Sorry, youtube search is not functional");
        }
        else{
            //do nothing
            messageEditor.setText("Not valid youtube lookup");
        }
    }

    /*
    public void getResult(Iterator<SearchResult> iterator, String query){
        if (!iterator.hasNext()) {
            messageEditor.setText("No results :(");
        }

        while (iterator.hasNext()) {

            SearchResult singleVideo = iterator.next();
            ResourceId rId = singleVideo.getId();

            // If video
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                String url = thumbnail.getUrl();
            }
        }
    }
    */

}
