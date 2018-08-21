package com.example.bmflo.group_chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bmflo on 8/19/2018.
 */

public class ContactDBHelper extends SQLiteOpenHelper {


    private static final String TAG = "ContactDBHelper";
    private static final String TABLE_NAME = "contact_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "email";
    private static final String COL4 = "username";

    public ContactDBHelper(Context context) { super(context, TABLE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT ," + COL3 + " TEXT ," + COL4 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, user.getName());
        contentValues.put(COL3, user.getEmail());
        contentValues.put(COL4, user.getUsername());

        Log.d(TAG, "addData: Adding " + user.getName() + " to " + TABLE_NAME);

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

    public void deleteContact(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    public ArrayList<User> getAllContacts(){
        ArrayList<User> arrayList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            User currentUser = new User(res.getString(res.getColumnIndex(COL2)), res.getString(res.getColumnIndex(COL4)),res.getString(res.getColumnIndex(COL3)));

            arrayList.add(currentUser);
            res.moveToNext();
        }
        return arrayList;
    }

    public User getUserWithName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        User result = new User(name, res.getString(res.getColumnIndex(COL4)), res.getString(res.getColumnIndex(COL3)));
        return result;
    }

    public User getUserWithUsername(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL4 + " = '" + name + "'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        User result = new User(res.getString(res.getColumnIndex(COL2)), name, res.getString(res.getColumnIndex(COL3)));
        return result;
    }

    public User getUserWithEmail(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = '" + name + "'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        User result = new User(res.getString(res.getColumnIndex(COL2)), res.getString(res.getColumnIndex(COL4)), name);
        return result;
    }


    public void deleteDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
