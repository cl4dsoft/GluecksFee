/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diddydevelopment.hardgame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jones
 */
public class SoundManager {

    Map<String, Sound> sounds;
    Music song;

    
    public SoundManager() {
         sounds = new HashMap<String, Sound>();
        
    }
    
    public void playSound(String name){
        Sound s=sounds.get(name);
        
        if(s==null){
            s = Gdx.audio.newSound(Gdx.files.internal("sounds/"+name+".mp3"));
            sounds.put(name, s);
        }
        
        s.stop();
        s.play();
        
        
    }
    
    public void playMusic(String name){
        if(song!=null){
            song.stop();
            song.dispose();
        }
        
        song = Gdx.audio.newMusic(Gdx.files.internal("music/"+name+".mp3"));
       // m.setVolume(0.5f);
        song.setLooping(true);

        song.play();
        
        
    }
    
    
}
