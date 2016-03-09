package com.diddydevelopment.hardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

	public static Texture PLAYER = new Texture(Gdx.files.internal("player.png"));
	public static Texture ENEMY = new Texture(Gdx.files.internal("enemy.png"));
	public static Texture OBSTACLE = new Texture(Gdx.files.internal("obstacle.png"));
	public static Texture SPACE = new Texture(Gdx.files.internal("space.png"));
	public static Texture VOID = new Texture(Gdx.files.internal("void.png"));
	
}
