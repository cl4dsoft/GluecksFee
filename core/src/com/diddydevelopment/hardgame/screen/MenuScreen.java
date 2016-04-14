/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.diddydevelopment.hardgame.camera.OrthoCamera;
import com.diddydevelopment.hardgame.sound.SoundManager;

/**
 *
 * @author jones
 */
public class MenuScreen implements Screen {

    private Game game;

    private OrthoCamera camera;
    private ShapeRenderer sr;
    private SoundManager soundManager;

    ParticleEffect pe;
    SpriteBatch batch;

    Skin skin;
    Stage stage;
    BitmapFont font;
    Texture titleText;

    public MenuScreen(Game g) {
        game = g;
        camera = new OrthoCamera();
        soundManager = new SoundManager();
        
        
        soundManager.playMusic("menu");

        sr = new ShapeRenderer();

        batch = new SpriteBatch();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particleEffects/starFlash"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pe.getEmitters().first().getSpawnWidth().setHigh(Gdx.graphics.getWidth());
        pe.getEmitters().first().getSpawnHeight().setHigh(Gdx.graphics.getHeight());
        pe.start();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        
         titleText = new Texture("buttonTextures/title.png");

        Texture menuPlay = new Texture("buttonTextures/menuPlay.png");

        skin.add("white", menuPlay);

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
        textButton.setSize(Gdx.graphics.getWidth() * 0.8f, menuPlay.getHeight());
        textButton.setPosition(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() *0.25f);
        stage.addActor(textButton);

        textButton.addListener(new ChangeListener() {
            private boolean changed=false;
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
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
        camera.update();
    }

    @Override
    public void render(float delta) {
        update();

        //sb.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        pe.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        pe.draw(batch);
        
        batch.draw(titleText, Gdx.graphics.getWidth() /2 - titleText.getWidth()/2 , Gdx.graphics.getHeight() / 4*3);

        font.draw(batch, "Created by\n  Christian, Jonas & Christian", Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 8);

        batch.end();
        if (pe.isComplete()) {
            pe.reset();
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
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
