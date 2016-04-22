/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.level;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.diddydevelopment.hardgame.entity.Collectable;
import com.diddydevelopment.hardgame.entity.Entity;
import com.diddydevelopment.hardgame.entity.EntityManager;
import com.diddydevelopment.hardgame.sound.SoundManager;
import static java.lang.Math.min;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Level {
        public static OrthographicCamera camera;
        
    public static int sizeX = -1;
    public static int sizeY = -1;
    
    public static int tileSize = -1;
    public static float borderX = -1;
    public static float borderY = -1;
    
    public static Vector2 playerStart;
    static TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    
    public static SoundManager soundManager;
    
    ArrayList<Entity> entities;
    
    public Level() {
        loadLevel(5);
     }
          
    public boolean loadLevel(int level) {
        String name="levels/level"+level+".tmx";
        
         if(!Gdx.files.internal(name).exists()){
            return false;
        }
        
        map = new TmxMapLoader().load(name);
        
       
        float unitScale = 1;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        
        sizeX = map.getProperties().get("width", Integer.class);
        sizeY = map.getProperties().get("height", Integer.class);
        
        
        initSizes();
        placeEntities();
        soundManager.playMusic(map.getProperties().get("music",String.class));
        
        return true;
    }
    
    public void initSizes() {
        float w = camera.viewportWidth;
        float h = camera.viewportHeight;
        int tileSizeW = map.getProperties().get("tilewidth", Integer.class);
        int tileSizeH = map.getProperties().get("tileheight", Integer.class);
        
        tileSize = min(tileSizeW,tileSizeH);
        
        borderX = (w - tileSize * sizeX)/2;
        borderY = (h - tileSize * sizeY)/2;
        
    }
    
    public void placeEntities(){
         MapLayer objects=map.getLayers().get("objects");
        MapObjects objs=objects.getObjects();
        
        
        for (MapObject object : objs) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                
                Vector2 posOnMap=new Vector2(rectangleObject.getRectangle().getX()+borderX,rectangleObject.getRectangle().getY()+borderY);

                if (rectangleObject.getName().equals("start")) {
                    this.playerStart = posOnMap;
                }else if (rectangleObject.getName().equals("gem")) {
                    Entity gem=new Collectable(posOnMap,new Vector2(tileSize,tileSize),new float[]{1,0,1});
                     EntityManager.addEntity(gem);
                
                }
                
            }
        }
        
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
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("tile1");
       return (layer.getCell((int)v.x, (int)v.y).getTile().getId()==1);
    }
    
    public void render(SpriteBatch sb, ShapeRenderer sr) {
     
        float oldX=camera.position.x;
        float oldY=camera.position.y;
        
        camera.position.x-= borderX;
        camera.position.y-= borderY;
        
        camera.update();
       
       
        renderer.setView(camera);
        renderer.render();
        
        camera.position.x=oldX;
        camera.position.y=oldY;
        camera.update();
    }    
}
