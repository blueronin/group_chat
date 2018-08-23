package com.example.bmflo.group_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by bmflo on 8/19/2018.
 */

public class RegisterActivity extends Activity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private ContactDBHelper dbHelper;
    ArrayList<User> allUsers;

    Button registerButton;

    EditText nameEditText;
    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    TextView dupWarn;

    boolean dup;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new ContactDBHelper(this);
        allUsers = dbHelper.getAllContacts();

        registerButton = (Button) findViewById(R.id.register_button);

        nameEditText = (EditText) findViewById(R.id.name_field);
        usernameEditText = (EditText) findViewById(R.id.user_field);
        emailEditText = (EditText) findViewById(R.id.email_field);
        passwordEditText = (EditText) findViewById(R.id.password_field);
        dupWarn = (TextView) findViewById(R.id.duplicate_warning);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

    }


    public void register(View view){
        dupWarn.setVisibility(View.GONE);
        dup = false;
        final String name = nameEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        final String username = usernameEditText.getText().toString();

        for(User u:allUsers){
            if(username.equals(u.getUsername())){
                //duplicate username
                dupWarn.setVisibility(View.VISIBLE);
                dup = true;
            }
        }
        if(dup==true){
            return;
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        user = mAuth.getCurrentUser();
                        //Store User details
                        addUserNameToUser(user);

                        User userInstance = new User(name, username, email);
                        User dummyUser = new User("none", username, "none");
                        dbHelper.addData(dummyUser); //add dummy so local db does not have other user info besides username

                        dbRef.child("users").child(username).setValue(userInstance);

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        RegisterActivity.this.finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }



    private void addUserNameToUser(FirebaseUser user) {
        String name = nameEditText.getText().toString();
        String username = usernameEditText.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

}
