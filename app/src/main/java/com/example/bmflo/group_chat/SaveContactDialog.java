package com.example.bmflo.group_chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        contactDBHelper = new ContactDBHelper(getActivity());
        myContacts = contactDBHelper.getAllContacts();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("User");

        View view = inflater.inflate(R.layout.dialog_add_contact, null);

        search = (EditText) view.findViewById(R.id.search_view);

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
                //saveToContacts();
            }
        });

        builder.setTitle("Add Contact").setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Update db
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void searchUser(){
        String email = search.getText().toString();

        //UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
// See the UserRecord reference doc for the contents of userRecord.
        //System.out.println("Successfully fetched user data: " + userRecord.getEmail());
    }

}
