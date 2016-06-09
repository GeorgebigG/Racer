package com.mygdx.game.ActivityPackage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mygdx.game.R;
import com.mygdx.game.SQL.NameDateTime;
import com.mygdx.game.SQL.NameHighscore;
import com.mygdx.game.Screens.PlayScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dieActivity extends Activity {
    TextView highscoreText, scoreText, Player, Date, Time;
    long highscore;
    SharedPreferences sPref;
    public static final String LONG_KEY = "LONG_KEY";
    public static long score;
    public boolean playAgain = false;

    public static String name;

    public static long sTime;

    public static String DateForBase;
    public static String TimeForBase;

    long seconds;
    long minutes;

    public NameHighscore nameHighscore;
    public NameDateTime nameDateTime;
    SQLiteDatabase database;
    SQLiteDatabase historyDatabase;

    TextView gameOver, yourHighscore, yourScore, PlayerN, Day, youArePlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_die);

        seconds = sTime;
        DateForBase = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        while (seconds - 60 >= 0) {
            seconds -= 60;
            minutes++;
        }

        if (seconds < 10)
            TimeForBase = minutes + ":0" + seconds + getResources().getString(R.string.second);
        else
            TimeForBase = minutes + ":" + seconds + getResources().getString(R.string.second);

        highscoreText = (TextView) findViewById(R.id.Highscore);
        scoreText = (TextView) findViewById(R.id.score);
        Player = (TextView) findViewById(R.id.Player);
        Date = (TextView) findViewById(R.id.Date);
        Time = (TextView) findViewById(R.id.time);

        gameOver = (TextView) findViewById(R.id.gameOver);
        gameOver.setText(getResources().getString(R.string.gameOver));
        yourHighscore = (TextView) findViewById(R.id.yourHighscore);
        yourHighscore.setText(getResources().getString(R.string.yourHighScore));
        yourScore = (TextView) findViewById(R.id.yourScore);
        yourScore.setText(getResources().getString(R.string.yourScore));
        PlayerN = (TextView) findViewById(R.id.PlayerN);
        PlayerN.setText(getResources().getString(R.string.PlayerName));
        Day = (TextView) findViewById((R.id.Day));
        Day.setText(getResources().getString(R.string.Day));
        youArePlaying = (TextView) findViewById(R.id.youArePlaying);
        youArePlaying.setText(getResources().getString(R.string.youArePlaying));

        Date.setText(DateForBase);

        Time.setText(TimeForBase);

        Player.setText(name);

        highscore = getData();
        if (score > highscore) {
            saveData(score);
            highscore = score;
        }

        highscoreText.setText(highscore + "");
        scoreText.setText(score + "");

        saveDataToHistory();
    }

    public void saveDataToHistory() {
        nameDateTime = new NameDateTime(this);
        historyDatabase = nameDateTime.getWritableDatabase();

        historyDatabase.delete(NameDateTime.TABLE_NAME, null, null);

        ContentValues base = new ContentValues();
        base.put(NameDateTime.COLUMN_NAME, name);
        base.put(NameDateTime.COLUMN_DATE, DateForBase);
        base.put(NameDateTime.COLUMN_TIME, TimeForBase);
        base.put(NameDateTime.COLUMN_SCORE, score);

        historyDatabase.insert(NameDateTime.TABLE_NAME, null, base);
    }

    public void onClick(View v) {
        nameHighscore = new NameHighscore(this);
        database = nameHighscore.getWritableDatabase();

        switch (v.getId()) {
            case R.id.again:
                onBackPressed();
                break;
            case R.id.history:
                Intent intent = new Intent(this, HistoryActivity.class);
                HistoryActivity.database = database;
                HistoryActivity.name = name;
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void saveData(long score) {
        nameHighscore = new NameHighscore(this);
        database = nameHighscore.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from " + NameHighscore.TABLE_NAME + " where " + NameHighscore.COLUMN_NAME + " = \"" + name + "\"", null);

        if (cursor.moveToFirst()) {
            database.execSQL("update " + NameHighscore.TABLE_NAME + " set " + NameHighscore.COLUMN_HIGHSCORE + " = " + score + " where " +
                    NameHighscore.COLUMN_NAME + " = \"" + name + "\"");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NameHighscore.COLUMN_NAME, name);
            contentValues.put(NameHighscore.COLUMN_HIGHSCORE, score);

            database.insert(NameHighscore.TABLE_NAME, null, contentValues);
        }
    }


    public Long getData() {
        long score = 0;
        nameHighscore = new NameHighscore(this);
        database = nameHighscore.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select max(" + NameHighscore.COLUMN_HIGHSCORE + ") " +
                " from " + NameHighscore.TABLE_NAME + " where " + NameHighscore.COLUMN_NAME + " = \"" + name + "\"", null);

        //Select max(highscore) from DATA_BASA where name = "NAME"

        if (cursor.moveToFirst()) {

            do {
                score = cursor.getLong(0);
            } while (cursor.moveToNext());
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NameHighscore.COLUMN_NAME, name);
            contentValues.put(NameHighscore.COLUMN_HIGHSCORE, (long) 0);
            database.insert(NameHighscore.TABLE_NAME, null, contentValues);
        }

        return score;
    }


    @Override
    public void onBackPressed() {
        PlayScreen.setEnemyOption();
        PlayScreen.score = 0;
        PlayScreen.mainMusic.play();
        playAgain = true;
        PlayScreen.Time = 0;
        super.onBackPressed();
        if (!playAgain)
            onDestroy();
    }
}
