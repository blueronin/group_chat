package com.example.bmflo.group_chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
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
 * Created by bmflo on 8/20/2018.
 */

public class SaveContactDialog extends DialogFragment {

    private ContactDBHelper contactDBHelper;
    private ArrayList<User> myContacts;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private EditText search;
    private ImageButton addButton;
    private ImageButton searchButton;
    private TextView result;

    private RelativeLayout resultLayout;

    User searchedUser;
    User currentUser;

    String s;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Get Current user and convert to local user instance
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        currentUser = snapshot.getValue(User.class);
                        s = snapshot.child("contactString").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //got user

        contactDBHelper = new ContactDBHelper(getActivity());
        myContacts = contactDBHelper.getAllContacts();

        //showCurrentUser();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("users");

        View view = inflater.inflate(R.layout.dialog_add_contact, null);

        search = (EditText) view.findViewById(R.id.search_view);
        result = (TextView) view.findViewById(R.id.result);
        resultLayout = (RelativeLayout) view.findViewById(R.id.resultEntry);

        searchButton = (ImageButton) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser();
            }
        });

        addButton = (ImageButton) view.findViewById(R.id.saveContactButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToContacts();
                String message = "Added "+searchedUser.getUsername()+"!";
                result.setText(message);
            }
        });

        builder.setTitle("Add Contact").setView(view).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    public void searchUser(){
        String username = search.getText().toString();
        search.setText("");

        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        searchedUser = snapshot.getValue(User.class);
                        resultLayout.setVisibility(View.VISIBLE);
                        String userInfo = (searchedUser.getUsername()+"\n"+searchedUser.getName()+"\n"+searchedUser.getEmail());
                        result.setText(userInfo);
                    }
                }
                else{
                    result.setText("User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveToContacts(){
        //saved to local db and modifies firebase db with each member's new contact


        contactDBHelper.addData(searchedUser);
        String updatedContactString1 = searchedUser.getContactString()+currentUser.getUsername()+","; //new list of contacts to searched user
        String updatedContactString2 = currentUser.getContactString()+searchedUser.getUsername()+","; //new list of contacts for current user


        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put(currentUser.getUsername()+"/contactString", updatedContactString2);
        userUpdates.put(searchedUser.getUsername()+"/contactString", updatedContactString1);

        dbRef.updateChildren(userUpdates);
    }

    public void showCurrentUser(){
        String check = currentUser.getUsername();
    }

}
