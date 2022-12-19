package com.example.musicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  : Bhuvaneshvar
 * Project : MusicApp
 * Date    : 10:24 PM
 **/

public class MusicDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String MUSIC_TABLE_NAME = "music";
    public static final String MUSIC_COLUMN_ID = "id";
    public static final String MUSIC_COLUMN_NAME = "name";
    public static final String MUSIC_COLUMN_ICON = "icon";
    public static final String MUSIC_COLUMN_RATE = "rate";

    public MusicDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public boolean addSong(Song s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", s.name);
        contentValues.put("icon", s.icon);
        contentValues.put("rate", s.rate);
        contentValues.put("url", s.url);
        db.insert(MUSIC_TABLE_NAME, null, contentValues);
        return true;
    }

    public List<Song> getAllSong() {
        List<Song> list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from music", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            String icon = res.getString(2);
            int rate = res.getInt(3);
            String url = res.getString(4);
            list.add(new Song(id, name, icon, rate, url));
            res.moveToNext();
        }

        return list;
    }

    public int updateRate(int rate, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rate", rate);
        return
                db.update("music", cv, "id = ? ", new String[]{Integer.toString(id)});

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table music " +
                        "(id integer primary key, name text,icon text,rate integer,url text)"
        );

        Song s1 = new Song();
        addSong(s1);
        Song s2 = new Song();
        addSong(s2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS music");
        onCreate(db);
    }
}
