package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String CONTACTS_TABLE = "CONTACTS";
    public static final String AVATAR_COL = "AVATAR";
    public static final String FNAME_COL = "FNAME";
    public static final String LNAME_COL = "LNAME";
    public static final String MOBILE_COL = "MOBILE";
    public static final String HOME_COL = "HOME";
    public static final String EMAIL_COL = "EMAIL";
    public static final String ID_COL = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String inputString = "CREATE TABLE " + CONTACTS_TABLE + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AVATAR_COL + " BLOB, " + FNAME_COL + " TEXT, " + LNAME_COL + " TEXT,  " + MOBILE_COL + " TEXT, " + HOME_COL + " TEXT, " + EMAIL_COL + " TEXT)";

        db.execSQL(inputString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AVATAR_COL, contactModel.getAvatar());
        cv.put(FNAME_COL, contactModel.getfName());
        cv.put(LNAME_COL, contactModel.getlName());
        cv.put(MOBILE_COL, contactModel.getMobile());
        cv.put(HOME_COL, contactModel.getHome());
        cv.put(EMAIL_COL, contactModel.getEmail());

        long insert = db.insert(CONTACTS_TABLE, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public ContactModel getContact(int id){
        ContactModel selectedContact = new ContactModel();
        SQLiteDatabase db = this.getReadableDatabase();
        String inputString = "SELECT * FROM " + CONTACTS_TABLE + " WHERE " + ID_COL + " = " + id;
        Cursor cursor = db.rawQuery(inputString, null);

        if(cursor.moveToFirst()){
            do {
                selectedContact.setId(cursor.getInt(0));
                selectedContact.setAvatar(cursor.getBlob(1));
                selectedContact.setfName(cursor.getString(2));
                selectedContact.setlName(cursor.getString(3));
                selectedContact.setMobile(cursor.getString(4));
                selectedContact.setHome(cursor.getString(5));
                selectedContact.setEmail(cursor.getString(6));
            }while(cursor.moveToNext());
        }
        return selectedContact;
    }

    public boolean editContact(ContactModel contactModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AVATAR_COL, contactModel.getAvatar());
        cv.put(FNAME_COL, contactModel.getfName());
        cv.put(LNAME_COL, contactModel.getlName());
        cv.put(MOBILE_COL, contactModel.getMobile());
        cv.put(HOME_COL, contactModel.getHome());
        cv.put(EMAIL_COL, contactModel.getEmail());

        long insert = db.update(CONTACTS_TABLE, cv, ID_COL + " = " + contactModel.getId(), null);
        if(insert == -1)
            return true;
        else
            return false;
    }

    public List<String> getAllNames() {
        List<String> returnList = new ArrayList<>();
        String inputString = "SELECT * FROM " + CONTACTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(inputString, null);

        if(cursor.moveToFirst()){
            do {
                int contactID = cursor.getInt(0);
                byte[] avatar = cursor.getBlob(1);
                String fName = cursor.getString(2);
                String lName = cursor.getString(3);
                String mobile = cursor.getString(4);
                String home = cursor.getString(5);
                String email = cursor.getString(6);

                ContactModel newContact = new ContactModel(contactID, avatar, fName, lName, mobile, home, email);
                returnList.add(newContact.displayName());
            } while(cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteContact(int id) {
        String inputString = "DELETE FROM " + CONTACTS_TABLE + " WHERE " + ID_COL + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(inputString);
        db.close();
    }

    public void deleteAll() {
        String inputString = "DELETE FROM " + CONTACTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(inputString);
        db.close();
    }




}
