package com.diddydevelopment.hardgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.diddydevelopment.hardgame.HardGame;
import com.diddydevelopment.hardgame.level.Level;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;


public class Player extends Entity {

    private final EntityManager entityManager;
    private final OrthographicCamera camera;
    private long lastFire;
    
    private final int playerSpeedMax = 1000;
    
    //wie schnell darf man tatschen in ms
    private int touchDelay = 300;
    private long lastTouched = 0;
    
    //wird jedes mal abgezogen
    private int gravity = 20;
    
    //speedboost beim jump in y richtung
    private int speedIncJump = 600;
    
    //speedinc in links rechts richtung
    private int speedInc = 400;
    //speeddec in links rechts richtung
    private int speedDec = 10;
    
    private int score = 0;
    
    
    // graphics
    Animation animation;
    Texture texture;
    TextureRegion[] frames;
    TextureRegion currentFrame;
    float time;
    
    

    public Player(EntityManager em, OrthographicCamera camera) 
    {
        super(new Vector2(0,0), new Vector2(Level.tileSize*1.0f , Level.tileSize*1.0f), new float[]{0.2f, 0.2f, 0.2f});
        this.entityManager = em;
        this.camera = camera;
        
        texture = new Texture( Gdx.files.internal( "fee2.png" ) );
        TextureRegion[][] tmp = TextureRegion.split( texture , texture.getWidth()/8 , texture.getHeight() );
        frames = new TextureRegion[8];
        for ( int i = 0 ; i < 8 ; ++i )
        {
            frames[i] = tmp[0][i];
        }
        animation = new Animation( 0.02f , frames );
        time = 0.0f;
        
        this.speedMax = playerSpeedMax;
    }
    
    

    @Override
    public void update() {

        
        
        boolean top = false;
        boolean bottom = false;
        boolean left = false;
        boolean right = false;
        boolean accept = false;
        boolean back = false;
        
        boolean wasTouched = false;
        double touchedX=0;
        
        if (Gdx.input.isTouched()) {
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0.0f));
            
            long curTime = System.currentTimeMillis();
            if(this.lastTouched+this.touchDelay<curTime) {
                wasTouched = true;
                touchedX = ((touch.x / HardGame.WIDTH)-0.5)*2;
                this.lastTouched = curTime;
            }
            
            if (touch.y < HardGame.HEIGHT / 5) { //top
                bottom = true;
            }
            if (touch.y > HardGame.HEIGHT - HardGame.HEIGHT / 5) { //bottom
                top = true;
            }
            if (touch.x < HardGame.WIDTH / 5) { //left
                left = true;
            }
            if (touch.x > HardGame.WIDTH - HardGame.WIDTH / 5) { //right
                right = true;
            }
            
        }
        if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
            top = true;
        }
        if (Gdx.input.isKeyPressed(Keys.S)  || Gdx.input.isKeyPressed(Keys.DOWN)) {
            bottom = true;
        }
        
        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            left = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)  || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            right = true;
        }
        
        
        if(left&&right) {
            left = false;
            right = false;
        }
        
        if(top&&bottom) {
            top = false;
            bottom = false;
        }
        
        direction.y -= gravity;

        if(wasTouched) {
            soundManager.playSound("jump");
            direction.y = direction.y+speedIncJump;
            direction.x = (float) (direction.x+touchedX*speedInc);
        }
        
//        if(top) direction.y = direction.y+speedInc;
//        if(bottom) direction.y = direction.y-speedInc;
//        if(left) direction.x = direction.x-speedInc;
//        if(right) direction.x = direction.x+speedInc;
//        
        if(direction.x > 0) direction.x = max(0,direction.x - speedDec);
        if(direction.x < 0) direction.x = min(0,direction.x + speedDec);
        if(direction.y > 0) direction.y = max(0,direction.y - speedDec);
        if(direction.y < 0) direction.y = min(0,direction.y + speedDec);
        
        if (direction.x < -speedMax) direction.x = -speedMax;
        if (direction.y < -speedMax) direction.y = -speedMax;
        if (direction.x > speedMax) direction.x = speedMax;
        if (direction.y > speedMax) direction.y = speedMax;
        

        Vector2 posOld = new Vector2(this.pos);
        
        Vector2 posAdd = new Vector2(direction).scl(Gdx.graphics.getDeltaTime());
        Vector2 realnewPos = new Vector2(posOld).add(posAdd);
        //collision detection
        //Gdx.app.log("collisionDetection", "Old Pos "+pos+" Movevector "+t+" New Pos "+new Vector2(pos).add(t));
        
        Vector2 newPosX = new Vector2(this.pos.x+posAdd.x,this.pos.y);
        Vector2 newPosY = new Vector2(this.pos.x,this.pos.y+posAdd.y);
        
       // Gdx.app.log("col",pos.toString());
        
//        for(Vector2 asdf : colTiles) {
//            Gdx.app.log("collisionDetection", asdf.toString());
//        }
        
        ArrayList<Vector2> colTiles;
        
        pos = newPosX;
        colTiles = (ArrayList<Vector2>) this.collidesWithLevelTiles();

        if(direction.x > 0) { //right
            for (Vector2 tile : colTiles) {
                if(!Level.isWalkable(tile)) {
                    direction.x = 0;
                    Vector2 pixelVal = Level.fromTileToPixel(tile);
                    realnewPos.x = pixelVal.x - this.size.x;
                    
                }
            }
        } else if(direction.x < 0) {//left
            for (Vector2 tile : colTiles) {
                if(!Level.isWalkable(tile)) {
                    direction.x = 0;
                    Vector2 pixelVal = Level.fromTileToPixel(tile);
                    realnewPos.x = pixelVal.x+Level.tileSize;
                    break;
                }
            }
        }
        
        this.pos = newPosY;
        colTiles = (ArrayList<Vector2>) this.collidesWithLevelTiles();

        if(direction.y > 0) {//top

            for (Vector2 tile : colTiles) {
                if(!Level.isWalkable(tile)) {
                    direction.y = 0;
                    Vector2 pixelVal = Level.fromTileToPixel(tile);
                    realnewPos.y = pixelVal.y - this.size.y;
                    break;
                }
            }
            
        } else if(direction.y < 0) { //down

            for (Vector2 tile : colTiles) {
                if(!Level.isWalkable(tile)) {
                    direction.y = 0;
                    Vector2 pixelVal = Level.fromTileToPixel(tile);
                    realnewPos.y = pixelVal.y+Level.tileSize;
                    break;
                }
            }
            
        }
        
        pos = realnewPos;
    }
    
    
    
    @Override
    public void render( SpriteBatch sb , ShapeRenderer sr )
    {
        time += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame( time , true );
        sb.begin();
        sb.draw( currentFrame , this.pos.x-8 ,this.pos.y-8 , this.size.x+16 , this.size.y+16 );
        sb.end();
    }
    
    
    
    public void setScore(int a){
        score=a;
    } 
    
    public int getScore(){
        return score;
    }

}
