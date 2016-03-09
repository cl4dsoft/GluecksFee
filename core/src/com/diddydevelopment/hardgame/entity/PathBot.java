/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.level.Level;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author chris
 */
public class PathBot extends Entity{

    
    int currentWaypoint;
    ArrayList<Vector2> waypoints;
    
    
    public PathBot(ArrayList<Vector2> waypoints) {
        super(Level.fromTileToPixel(waypoints.get(0)), new Vector2(Level.tileSize,Level.tileSize), new float[]{1,0,0});
        this.speedMax = 1;
        currentWaypoint = 1;
        this.waypoints = waypoints;
        for (int i=0;i<this.waypoints.size();++i) {
            this.waypoints.get(i).set(Level.fromTileToPixel(this.waypoints.get(i)));
        }
    }

    @Override
    public void update() {
        this.moveTowardsNextWaypoint();
    }
    
    public void moveTowardsNextWaypoint() {
        if(pos.x < waypoints.get(currentWaypoint).x){ //move right
            pos.x = pos.x + this.speedMax;
            if(pos.x >= waypoints.get(currentWaypoint).x) {
                pos.x = waypoints.get(currentWaypoint).x;
                        this.currentWaypoint = (currentWaypoint+1) % (this.waypoints.size());

            }
        } else if(pos.x > waypoints.get(currentWaypoint).x) { //move left
            pos.x = pos.x - this.speedMax;
            if(pos.x <= waypoints.get(currentWaypoint).x) {
                pos.x = waypoints.get(currentWaypoint).x;
                        this.currentWaypoint = (currentWaypoint+1) % (this.waypoints.size());

            }
        }
        
        if(pos.y < waypoints.get(currentWaypoint).y){ //move up
            pos.y = pos.y + this.speedMax;
            if(pos.y >= waypoints.get(currentWaypoint).y) {
                pos.y = waypoints.get(currentWaypoint).y;
                        this.currentWaypoint = (currentWaypoint+1) % (this.waypoints.size());

            }
        } else if(pos.y > waypoints.get(currentWaypoint).y){ //move down
            pos.y = pos.y - this.speedMax;
            if(pos.y <= waypoints.get(currentWaypoint).y) {
                pos.y = waypoints.get(currentWaypoint).y;
                        this.currentWaypoint = (currentWaypoint+1) % (this.waypoints.size());

            }
        }
        
    }
    
    
}
