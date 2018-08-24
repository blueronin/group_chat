package com.example.bmflo.group_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by bmflo on 8/23/2018.
 */

public class ProfileActivity extends Activity {

    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String eMail = intent.getStringExtra("email");

        setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);

        username.setText(name);
        email.setText(eMail);
    }
}
