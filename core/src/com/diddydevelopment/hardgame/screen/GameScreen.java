package com.diddydevelopment.hardgame.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.camera.OrthoCamera;
import com.diddydevelopment.hardgame.entity.EntityManager;
import com.diddydevelopment.hardgame.entity.Sprite;
import com.diddydevelopment.hardgame.level.Level;
import com.diddydevelopment.hardgame.sound.SoundManager;

public class GameScreen extends Screen {

	private OrthoCamera camera;
	private EntityManager entityManager;
	private ShapeRenderer sr;
        private Level lvl;
        private SoundManager soundManager;
        
        
        private Sprite test;
        
	@Override
	public void create() {
		camera = new OrthoCamera();
		entityManager = new EntityManager(camera);
                soundManager = new SoundManager();
                Sprite.soundManager=soundManager;
                Level.camera=camera;
                Level.soundManager=soundManager;
                lvl = new Level();
                entityManager.setPlayer();
                
                sr = new ShapeRenderer();
                
                test = new Sprite(new Vector2(50,50), new Vector2(50,50), new float[]{1,1,0});
                
	}

	@Override
	public void update() {
		camera.update();
		entityManager.update();
                
                if(entityManager.getPlayer().collides(test)) {
                    test.color = new float[]{1,1,0};
                } else {
                    test.color = new float[]{0,1,1};

                }
                
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
                sr.setProjectionMatrix(camera.combined);
                sr.setColor(1, 1, 1, 1);

                sr.begin(ShapeType.Filled);
                lvl.render(sb, sr);
		entityManager.render(sb,sr);
                test.render(sb, sr);
                sr.end();

	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

}
