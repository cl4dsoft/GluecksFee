/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author jones
 */
public class Collectable extends Entity{

    public Collectable(Vector2 pos, Vector2 size, float[] color) {
        super(pos, size, color);
    }
    

    @Override
    public void update() {
        if(entityManager.getPlayer().collides(this)){
            entityManager.getPlayer().setScore(entityManager.getPlayer().getScore()+1);
            soundManager.playSound("pickup");
            entityManager.deleteEntity(this);
        }
    }
    
}
