package com.example.sqllitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Addressbook.db";
    private static final String CONTACTS_TABLE_NAME = "contacts";
    private static final String CONTACTS_COLUMN_ID = "id";
    private static final String CONTACTS_COLUMN_NAME = "name";
    private static final String CONTACTS_COLUMN_EMAIL = "email";
    private static final String CONTACTS_COLUMN_STREET = "street";
    private static final String CONTACTS_COLUMN_CITY = "place";
    private static final String CONTACTS_COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME +
                "(" + CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY," + CONTACTS_COLUMN_NAME + " TEXT," + CONTACTS_COLUMN_PHONE + " TEXT," + CONTACTS_COLUMN_EMAIL + " TEXT," + CONTACTS_COLUMN_STREET + " TEXT," + CONTACTS_COLUMN_CITY + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertContact(String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("street", street);
        values.put("place", place);
        db.insert("contacts", null, values);
        return true;
    }

    public Cursor getData(int Id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE_NAME + " WHERE " + CONTACTS_COLUMN_ID + " = " + Id, null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer Id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("street", street);
        values.put("place", place);
        db.update(CONTACTS_TABLE_NAME, values, CONTACTS_COLUMN_ID + " = ? ", new String[]{Integer.toString(Id)});
        return true;
    }

    public Integer deleteContact(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTACTS_TABLE_NAME, CONTACTS_COLUMN_ID + "= ? ", new String[]{Integer.toString(Id)});
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
}
