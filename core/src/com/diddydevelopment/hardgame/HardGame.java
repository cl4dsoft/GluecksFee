package com.diddydevelopment.hardgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.diddydevelopment.hardgame.screen.MenuScreen;

public class HardGame extends Game {

	public static int WIDTH = 480, HEIGHT = 800;
        
	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
		super.render();

	}
        

	@Override
	public void dispose() {
            screen.dispose();
	}



	@Override
	public void resize(int width, int height) {
             screen.resize(width, height);
	}

	@Override
	public void pause() {
            screen.pause();
	}

	@Override
	public void resume() {
        screen.resume();
	}
}

