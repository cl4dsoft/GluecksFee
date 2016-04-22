/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author jones
 */
public class Collectable extends Entity{

    private static Texture texture = new Texture( Gdx.files.internal( "diamond.png" ) );;
    
    public Collectable( Vector2 pos, Vector2 size, float[] color ) 
    {
        super( pos , size , color );
    }
    

    @Override
    public void update() {
        if(entityManager.getPlayer().collides(this)){
            entityManager.getPlayer().setScore(entityManager.getPlayer().getScore()+1);
            soundManager.playSound("pickup");
            entityManager.deleteEntity(this);
        }
    }
    
    @Override
    public void render( SpriteBatch sb , ShapeRenderer sr )
    {
        sb.begin();
        sb.draw( texture , this.pos.x , this.pos.y );
        sb.end();
    }
    
}
