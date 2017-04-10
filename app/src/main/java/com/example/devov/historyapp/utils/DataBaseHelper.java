package com.example.devov.historyapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by devov on 2016/10/21.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, int version) {
        super(context,"cach.db",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists CachTable(" +
                "id integer primary key autoincrement,phrase text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
