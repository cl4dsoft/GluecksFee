package com.diddydevelopment.hardgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diddydevelopment.hardgame.screen.GameScreen;
import com.diddydevelopment.hardgame.screen.MenuScreen;

public class HardGame extends Game {
        public static int WIDTH = 480, HEIGHT = 800;
        private OrthographicCamera camera;
        StretchViewport viewport;
        
	@Override
	public void create () {
                camera=new OrthographicCamera(WIDTH,HEIGHT);
                viewport=new StretchViewport(WIDTH, HEIGHT,camera);
            
                
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
             viewport.update(width, height);
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
        
        public OrthographicCamera getCamera(){
            return camera;
        }
        
          public Viewport getViewport(){
            return viewport;
        }
        
        public float getWidth(){
            return viewport.getWorldWidth();
        }
        
         public float getHeight(){
            return viewport.getWorldHeight();
        }
         
}

