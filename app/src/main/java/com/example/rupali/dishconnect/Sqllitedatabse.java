package com.example.rupali.dishconnect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqllitedatabse extends SQLiteOpenHelper {
    public Sqllitedatabse(Context context) {
        super(context, "harry", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE " + Table.name + "(" + Table.id + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                Table.urit + " TEXT)";

        String sql2="CREATE TABLE " + Table.imageTablename + "(" + Table.id2 + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                Table.urit2 + " TEXT)";

        String sql3="CREATE TABLE " + Table.commenttable + "(" + Table.id3 + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                Table.urit3 + " TEXT)";

        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
