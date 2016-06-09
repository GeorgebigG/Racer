package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.ActivityPackage.MainActivity;
import com.mygdx.game.ActivityPackage.dieActivity;
import com.mygdx.game.Screens.PlayScreen;

public class AndroidLauncher extends AndroidApplication implements PlayScreen.MyGameCallBack {
	public enum callActivity { MainActivity, dieActivity }
	public static callActivity callTo = callActivity.MainActivity;

	Bundle savedInstanceState;
	AndroidApplicationConfiguration config;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (callTo == callActivity.MainActivity) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			PlayScreen.myGameCallBack = this;
		}
		this.savedInstanceState = savedInstanceState;

		config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		initialize(new Main(), config);
	}

	@Override
	public void startActivity(long score, long Time) {
		dieActivity.sTime = Time;
		callTo = callActivity.dieActivity;
		Intent intent = new Intent(this, dieActivity.class);
		dieActivity.score = score;
		startActivity(intent);
	}
}
