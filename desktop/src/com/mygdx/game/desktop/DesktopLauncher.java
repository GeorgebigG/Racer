package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;

public class DesktopLauncher implements PlayScreen.MyGameCallBack {

	static Main main;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 797;
		config.width = 595;

		main = new Main();
		PlayScreen.myGameCallBack = new DesktopLauncher();

		new LwjglApplication(main, config);
	}

	@Override
	public void startActivity(long score, long Time) {
		main.setScreen(new PlayScreen(main));
	}
}
