/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.level.Level;
import com.diddydevelopment.hardgame.sound.SoundManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chris
 */
public class Sprite {
        public static SoundManager soundManager;
    
        public Vector2 pos;
        public Vector2 size;
        public float[] color;
        
        
        public Sprite(Vector2 pos, Vector2 size, float[] color) {
            this.pos = pos;
            this.size = size;
            this.color = color;
            
        }
    
    
    	public void render(SpriteBatch sb, ShapeRenderer sr) {
            sr.setColor(this.color[0], this.color[1], this.color[2], 1);
            sr.rect(this.pos.x,this.pos.y,this.size.x,this.size.y);
        }

        public boolean collides(Sprite s) {
            return this.getBounds().overlaps(s.getBounds());
        }
        
	public Vector2 getPosition() {
		return pos;
	}
	public void setPosition(Vector2 vec) {
		this.pos = vec;
	}
	public Rectangle getBounds() {
		return new Rectangle(pos.x, pos.y, size.x, size.y);
	}
        
        public List<Vector2> collidesWithLevelTiles() {
            float x1 = pos.x;
            float x2 = pos.x+ size.x-1;
            float y1 = pos.y;
            float y2 = pos.y+ size.y-1;
            
            Vector2 t1 = Level.fromPixeltToTile(new Vector2(x1,y1));
            Vector2 t2 = Level.fromPixeltToTile(new Vector2(x2,y2));
            
            
            ArrayList<Vector2> rtn = new ArrayList<Vector2>();
            for(int ii=(int)t1.x;ii<=(int)t2.x;++ii) {
                for(int jj=(int)t1.y;jj<=(int)t2.y;++jj) {
                    rtn.add(new Vector2(ii,jj));
                }
            }
            return rtn;
        }
}
