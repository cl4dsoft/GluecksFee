/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.level;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.HardGame;
import com.diddydevelopment.hardgame.entity.Entity;
import com.diddydevelopment.hardgame.entity.EntityManager;
import com.diddydevelopment.hardgame.entity.PathBot;
import static java.lang.Math.min;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Level {
        
    public static int sizeX = -1;
    public static int sizeY = -1;
    
    public static int tileSize = -1;
    public static float borderX = -1;
    public static float borderY = -1;
    
    public static Vector2 playerStart;
    /*
    0 = frei
    1 = hindernis
    2 = abgrund
    3 = start
    4 = ziel
    5 = item
    */
    public static int[][] map;
    
    
    ArrayList<Entity> entities;
    
    public Level() {
        loadDefault();
    }
    
    public void loadDefault() {
        sizeX = 50;
        sizeY = 50;
                
        this.map = new int[sizeX][sizeY];
        initSizes();

        for(int x=0;x<sizeX;++x) {
            for(int y=0;y<sizeY;++y) {
                this.map[x][y] = 0;
            }
        }
        map[0][0] = 3;
        this.playerStart = Level.fromTileToPixel(new Vector2(0,0));
        map[9][9] = 4;
        map[3][5] = 5;
        map[0][5] = 1;
        map[1][5] = 1;
        map[2][5] = 1;
        map[4][5] = 1;
        map[5][5] = 1;
        map[6][5] = 1;
        map[7][5] = 1;
        map[8][5] = 1;
        map[9][5] = 1;
        
        map[25][25] = 1;
        
        map[0][7] = 2;
        map[1][7] = 2;
        map[2][7] = 2;
        map[0][8] = 2;
        map[1][8] = 2;
        map[2][8] = 2;
        map[0][9] = 2;
        map[1][9] = 2;
        map[2][9] = 2;
        
        ArrayList<Vector2> wps = new ArrayList<Vector2>();
        wps.add(new Vector2(30,30));
        wps.add(new Vector2(30,35));
        wps.add(new Vector2(35,30));
        
        
        EntityManager.addEntity(new PathBot(wps));
        
    }
    
    public void initSizes() {
        int h = HardGame.HEIGHT;
        int w = HardGame.WIDTH;
        int tileSizeW = w / sizeX;
        int tileSizeH = h / sizeY;
        
        tileSize = min(tileSizeW,tileSizeH);
        
        borderX = (w - tileSize * sizeX)/2;
        borderY = (h - tileSize * sizeY)/2;
        
    }
    
    public void loadFromFile(String name) {
        
    }
    
    Vector2 getPixelPos(Vector2 vec) {
        return new Vector2(borderX+tileSize*vec.x,borderY+tileSize*vec.y);
    }
    
    public static Vector2 fromPixeltToTile(Vector2 v) {
        return new Vector2((int)(v.x-borderX)/tileSize,(int)(v.y-borderY)/tileSize);
    }
    
    public static Vector2 fromTileToPixel(Vector2 v) {
        return new Vector2(v.x*tileSize+borderX,v.y*tileSize+borderY);
    }
    
    public static boolean isWalkable(Vector2 v) {
        
        if(v.x < 0 || v.y < 0 || v.x >= Level.sizeX || v.y >= Level.sizeY) {
            return false;
        }
        
        return (Level.map[(int)v.x][(int)v.y] != 1);
    }
    
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        
        for(int x=0;x<sizeX;++x) {
            for(int y=0;y<sizeY;++y) {
                sr.setColor(1, 1, 1, 1);
                if(map[x][y] == 0) sr.setColor(0.4f, 0.4f, 0.4f, 1);
                if(map[x][y] == 1) sr.setColor(0.8f, 0.8f, 0.8f, 1);
                if(map[x][y] == 2) sr.setColor(0, 0, 1, 1);
                if(map[x][y] == 3) sr.setColor(1, 1, 1, 1);
                if(map[x][y] == 4) sr.setColor(1, 1, 0, 1);
                if(map[x][y] == 5) sr.setColor(0, 1, 1, 1);
                sr.rect(borderX+x*tileSize, borderY+y*tileSize, tileSize, tileSize);
            }
        }
    }    
}
