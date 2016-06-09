package com.mygdx.game.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gio on 23/04/16.
 */
public class NameHighscore extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "DATABASE_BY_MY";
    public static String TABLE_NAME = "DATA_BASE";

    public static String COLUMN_NAME = "name";
    public static String COLUMN_HIGHSCORE = "surname";

    public NameHighscore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_NAME +
                " text," + COLUMN_HIGHSCORE + " Integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(db);
    }
}
