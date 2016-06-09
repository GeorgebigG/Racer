package com.mygdx.game.ActivityPackage;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.mygdx.game.R;
import com.mygdx.game.SQL.NameDateTime;
import com.mygdx.game.SQL.NameHighscore;

public class HistoryActivity extends Activity {
    public static TextView history;
    public static SQLiteDatabase database;
    public static String name;
    public static NameDateTime nameDataTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history = (TextView) findViewById(R.id.history);
        history.setMovementMethod(ScrollingMovementMethod.getInstance());

        writeHistory();
    }

    private void writeHistory() {
        nameDataTime = new NameDateTime(this);
        database = nameDataTime.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + NameDateTime.COLUMN_DATE + ", " + NameDateTime.COLUMN_TIME + ", " +
                NameDateTime.COLUMN_SCORE + " from " + NameDateTime.TABLE_NAME + " where " + NameDateTime.COLUMN_NAME + " = \"" +
                name + "\" order by " + NameDateTime.COLUMN_SCORE + " desc", null);

        if (cursor.moveToFirst()) {
            int dateIndex = cursor.getColumnIndex(NameDateTime.COLUMN_DATE);
            int timeIndex = cursor.getColumnIndex(NameDateTime.COLUMN_TIME);
            int scoreIndex = cursor.getColumnIndex(NameDateTime.COLUMN_SCORE);

            do {
                history.setText(history.getText() + "Date: " + cursor.getString(dateIndex) + " | Time: " + cursor.getString(timeIndex) +
                        " | Score: " + cursor.getString(scoreIndex) + "\n");
            } while (cursor.moveToNext());
        } else
            history.setText("No history!");
    }

    public void onClick(View view) {
        nameDataTime = new NameDateTime(this);
        database = nameDataTime.getWritableDatabase();

        database.delete(NameDateTime.TABLE_NAME, null, null);

        writeHistory();
    }
}
