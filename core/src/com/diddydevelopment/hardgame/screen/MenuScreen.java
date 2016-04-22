/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diddydevelopment.hardgame.HardGame;
import com.diddydevelopment.hardgame.sound.SoundManager;

/**
 *
 * @author jones
 */
public class MenuScreen implements Screen {

    private HardGame game;

    private ShapeRenderer sr;
    private SoundManager soundManager;

    ParticleEffect pe;
    SpriteBatch batch;

    Skin skin;
    Stage stage;
    BitmapFont font;
    Texture titleText;

    public MenuScreen(HardGame g) {
        game = g;
        soundManager = new SoundManager();
        
        
        soundManager.playMusic("gluecksfeemaintheme");

        sr = new ShapeRenderer();

        batch = new SpriteBatch();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particleEffects/starFlash"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(game.getWidth() / 2, game.getHeight()/ 2);
        pe.getEmitters().first().getSpawnWidth().setHigh(game.getWidth());
        pe.getEmitters().first().getSpawnHeight().setHigh( game.getHeight());
        pe.start();

        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        
         titleText = new Texture("buttonTextures/title.png");

       Texture menuPlay = new Texture("buttonTextures/menuPlay.png");

        
        skin.add("white",menuPlay);

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        bfont.getData().setScale(2.0f, 2.0f);
        skin.add("default", bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.WHITE);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        final TextButton textButton = new TextButton("PLAY", skin);
        textButton.setSize(game.getWidth() * 0.8f, menuPlay.getHeight());
        textButton.setPosition(game.getWidth() * 0.1f, game.getHeight() *0.25f);
        stage.addActor(textButton);

        textButton.addListener(new ClickListener() {
            private boolean changed=false;
            
            public void clicked(InputEvent event, float x, float y) {
                if(!changed){
                    game.setScreen(new GameScreen(game));
                    changed=true;
                }
            }
        });

    }

    @Override
    public void show() {

    }

    private void update() {
    }

    @Override
    public void render(float delta) {
        update();
        
        pe.update(Gdx.graphics.getDeltaTime());
          
        if (pe.isComplete()) {
            pe.reset();
        }
        
        batch.setProjectionMatrix(game.getCamera().combined);
        batch.begin();
        pe.draw(batch);
        
        font.draw(batch, "Created by\n  Christian, Jonas & Christian", game.getWidth() / 5, game.getHeight() / 8);

         batch.draw(titleText,game.getWidth()/4, game.getHeight()/3*2,game.getWidth()/2,titleText.getHeight());
        batch.end();
        
      

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
            
    }

    @Override
    public void hide() {
        dispose();
            
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        pe.dispose();
        soundManager.dispose();
    }

}
