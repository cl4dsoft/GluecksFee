package com.diddydevelopment.hardgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.diddydevelopment.hardgame.HardGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            cfg.height = HardGame.HEIGHT;
            cfg.width = HardGame.WIDTH;
            new LwjglApplication(new HardGame(), cfg);
	}
}
