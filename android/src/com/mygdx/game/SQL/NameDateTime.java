package com.mygdx.game.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gio on 23/04/16.
 */
public class NameDateTime extends SQLiteOpenHelper {
    public static int VERSION = 10;
    public static String DATABASE_NAME = "DATABASE";
    public static String TABLE_NAME = "DATA_BASE_HISTORY";

    public static String COLUMN_NAME = "Name";
    public static String COLUMN_DATE = "Date";
    public static String COLUMN_TIME = "Time";
    public static String COLUMN_SCORE = "score";


    public NameDateTime(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_NAME + " text," + COLUMN_DATE + " text," +
                COLUMN_TIME + " text," + COLUMN_SCORE + " Integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(db);
    }
}
