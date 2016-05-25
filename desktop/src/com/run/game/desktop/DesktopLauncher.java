package com.run.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.run.game.Runner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Runner.HEIGHT;
		config.width = Runner.WIDTH;
		config.title = "Runner";
		new LwjglApplication(new Runner(), config);
	}
}
