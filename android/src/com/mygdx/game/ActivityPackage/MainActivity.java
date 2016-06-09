package com.mygdx.game.ActivityPackage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mygdx.game.Main;
import com.mygdx.game.R;
import com.mygdx.game.Screens.PlayScreen;

public class MainActivity extends Activity {
    Button Ok;
    EditText Name;
    String name;
    TextView whoPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.name);
        Name.setHint(getResources().getString(R.string.Name));
        Ok = (Button) findViewById(R.id.start);
        Ok.setText(getResources().getString(R.string.startPlaying));
        whoPlaying = (TextView) findViewById(R.id.whoPlaing);
        whoPlaying.setText(getResources().getString(R.string.Player));

        PlayScreen.yourSpeedIs = getResources().getString(R.string.yourSpeed);
        PlayScreen.yourKMHFPS = getResources().getString(R.string.kmPh);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                if (!name.equals("")) {
                    Main.name = Name.getText().toString();
                    dieActivity.name = name;
                    onBackPressed();
                } else
                    Toast.makeText(MainActivity.this, "Write your name!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
