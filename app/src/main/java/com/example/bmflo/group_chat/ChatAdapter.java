package com.example.bmflo.group_chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
/**
 * Created by bmflo on 8/19/2018.
 */

public class ChatAdapter extends BaseAdapter {
    private ArrayList<Chat> chats;
    private LayoutInflater chatInfo;
    private Context context;
    private RecyclerView.ViewHolder holder;

    public ChatAdapter(Context c, ArrayList<Chat> myChats){
        chats = myChats;
        chatInfo = LayoutInflater.from(c);
        context=c;
    }

    @Override
    public int getCount() {return chats.size();}

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
        RelativeLayout chatLayout = (RelativeLayout) chatInfo.inflate(R.layout.chat_list_item_view, parent, false);

        ImageView imageThumb = (ImageView)chatLayout.findViewById(R.id.chat_thumb);
        TextView chatName = (TextView)chatLayout.findViewById(R.id.chat_name);

        Chat currentChat = chats.get(position);
        String name = currentChat.getChatName();

        //String testFunc = extractChatNameFromKey(currentChat.getChatName());
        //String currentChatS = currentChat.getChatName();
        int i = 0;

        //chatName.setText(currentChat.getChatName());
        chatName.setText(extractChatNameFromKey(currentChat.getChatName()));
        //numImages.setText(currentAlbum.albumImages.size());

        chatLayout.setTag(position);

        return chatLayout;
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
