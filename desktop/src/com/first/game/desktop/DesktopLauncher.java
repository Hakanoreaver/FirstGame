package com.first.game.desktop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.first.game.FirstGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		String os = System.getProperty("os.name");
		if(os.equals("Mac OS X")) {
			FirstGame.fileExtension = "core/assets/";
			config.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2;
			config.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2;
		}
		else {
			config.width = 1920;
			config.height = 1080;
		}

		config.forceExit = true;
		new LwjglApplication(new FirstGame(), config);
	}
}
