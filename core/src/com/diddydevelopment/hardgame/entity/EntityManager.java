package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.diddydevelopment.hardgame.level.Level;


public class EntityManager {

	public static Array<Entity> entities = new Array<Entity>();
	public static Player player;
        public OrthographicCamera camera;
	
	public EntityManager(OrthographicCamera camera) {
            this.camera = camera;
	}
        
        public void resetLevel(){
            setPlayer();
        }
        
        public void setPlayer() {
            player = new Player(this, camera);
            this.player.setPosition(Level.playerStart);
        }
        
        public static Player getPlayer() {
            return player;
        }
	
	public static void update() {
		for (Entity e : entities)
			e.update();

		player.update();
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr) {
		for (Entity e : entities)
			e.render(sb, sr);
		player.render(sb, sr);
	}
	
	
	public static void addEntity(Entity entity) {
		entities.add(entity);
	}
        
        public static void deleteEntity(Entity entity) {
		entities.removeValue(entity,false);
	}
        
        public static void deleteAllEntities(){
            entities.clear();
        }
        
        public static Array<Entity> getEntitiesByType(Class c){
            Array<Entity> included = new Array<Entity>();
            for (Entity entity : entities) {
                if(entity.getClass().equals(c)){
                    included.add(entity);
                }
            }
            return included;
        }
	
}
