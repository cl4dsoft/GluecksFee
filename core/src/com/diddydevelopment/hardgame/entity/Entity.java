package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends Sprite {

	public Vector2 direction;
        protected int speedMax = 100;
        public static EntityManager entityManager;
        
	public Entity(Vector2 pos, Vector2 size, float[] color) {
                super(pos,size,color);
                this.direction = new Vector2(0,0);
	}
	
	public abstract void update();
	

	public void setDirection(float x, float y) {
		direction.set(x, y);
		direction.scl(Gdx.graphics.getDeltaTime());
	}
        
        
	
}
