package com.example.mvvmexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mvvmexample.Utils.*;


public class SQLiteHelper extends SQLiteOpenHelper {
public static final String DATABASE_NAME= "herodb.sqlite";
private static final int DB_VERSION = 1;
    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
final String createTable= "create table "+TABLE_NAME+ "("+
        ID+ " integer primary key autoincrement,"+
        NAME+ " varchar(50),"+
        REALNAME+" varchar(50),"+
        FIRST_APP+" varchar(50),"+
        CREATEDBY+" varchar(20),"+
        PUBLISHER+" varchar(20),"+
        URL+" TEXT)";
sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
