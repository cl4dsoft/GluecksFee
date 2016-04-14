package com.diddydevelopment.hardgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.camera.OrthoCamera;
import com.diddydevelopment.hardgame.entity.Collectable;
import com.diddydevelopment.hardgame.entity.Entity;
import com.diddydevelopment.hardgame.entity.EntityManager;
import com.diddydevelopment.hardgame.entity.Sprite;
import com.diddydevelopment.hardgame.level.Level;
import com.diddydevelopment.hardgame.sound.SoundManager;

public class GameScreen implements Screen {
        private Game game;
    
        private SpriteBatch sb;

	private OrthoCamera camera;
	private EntityManager entityManager;
	private ShapeRenderer sr;
        private Level lvl;
        private SoundManager soundManager;
        
        
        private Sprite test;
        
        public GameScreen(Game g){  
            game=g;
        
            sb = new SpriteBatch();
		camera = new OrthoCamera();
		entityManager = new EntityManager(camera);
                Entity.entityManager=entityManager;
                soundManager = new SoundManager();
                Sprite.soundManager=soundManager;
                Level.camera=camera;
                Level.soundManager=soundManager;
                lvl = new Level();
                entityManager.setPlayer();
                
                sr = new ShapeRenderer();
                
                test = new Sprite(new Vector2(250,250), new Vector2(50,50), new float[]{1,1,0});
        }
        
	@Override
	public void show() {
                
	}

       static int level=1;
                
	
	private void update() {
		camera.update();
		entityManager.update();
                
                if(entityManager.getPlayer().collides(test)) {
                    test.color = new float[]{1,1,0};
                } else {
                    test.color = new float[]{0,1,1};

                }
                
                if(EntityManager.getEntitiesByType(Collectable.class).size==0){
                    level++;
                    entityManager.deleteAllEntities();
                    if(!lvl.loadLevel(level)){
                        level=1;
                        lvl.loadLevel(level);
                    }
                    entityManager.resetLevel();
                }
                
	}

	@Override
	public void render(float delta) {
                update();
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
        soundManager.dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

    @Override
    public void hide() {
    }

}
