package com.example.bmflo.group_chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bmflo on 8/19/2018.
 */

public class ChatDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "ChatDBHelper";
    private static final String TABLE_NAME = "chat_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "chat_name";
    private static final String COL3 = "members";

    public ChatDBHelper(Context context) { super(context, TABLE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT ," + COL3 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addData(Chat chat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, chat.getChatName());
        contentValues.put(COL3, chat.membersToString());

        Log.d(TAG, "addData: Adding " + chat.getChatName() + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result<0){
            return false;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteChat(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    public ArrayList<Chat> getAllChats(){
        ArrayList<Chat> arrayList = new ArrayList<Chat>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Chat currentChat = new Chat();

            currentChat.setChatName(res.getString(res.getColumnIndex(COL1)));
            String allMembers = res.getString(res.getColumnIndex(COL3));
            ArrayList<String> allMembersArray = currentChat.stringToMembers(allMembers);
            currentChat.setMembers(allMembersArray);

            arrayList.add(currentChat);
            res.moveToNext();
        }
        return arrayList;
    }

    public Chat getChat(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        String allMembers = res.getString(res.getColumnIndex(COL3));
        Chat result = new Chat();
        ArrayList<String> allMembersArray = result.stringToMembers(allMembers);
        result.setChatName(name);
        result.setMembers(allMembersArray);
        return result;
    }

    public void deleteDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
