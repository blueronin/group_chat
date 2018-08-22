package com.example.bmflo.group_chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bmflo on 8/19/2018.
 */

public class ContactAdapter extends BaseAdapter {
    private ArrayList<User> contacts;
    private LayoutInflater contactInfo;
    private Context context;
    private RecyclerView.ViewHolder holder;

    public ContactAdapter(Context c, ArrayList<User> myContacts){
        contacts = myContacts;
        contactInfo = LayoutInflater.from(c);
        context=c;
    }

    @Override
    public int getCount() {return contacts.size();}

    @Override
    public Object getItem(int arg0){
        return null;
    }

    @Override
    public long getItemId(int arg0){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RelativeLayout chatLayout = (RelativeLayout) contactInfo.inflate(R.layout.contact_list_item_view, parent, false);

        ImageView contactIcon = (ImageView)chatLayout.findViewById(R.id.contact_icon);
        TextView contactName = (TextView)chatLayout.findViewById(R.id.contact_name);

        User currentCContact = contacts.get(position);

        contactName.setText(currentCContact.getName());

        chatLayout.setTag(position);

        return chatLayout;
    }
}
