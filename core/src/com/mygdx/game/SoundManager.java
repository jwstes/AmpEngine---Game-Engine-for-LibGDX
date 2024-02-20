package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import java.util.HashMap;
import java.util.Map;

public class SoundManager{
	private Map<String, Sound> sounds;
	private boolean soundPlaying;
	private long currentSoundID;
	private Sound currentSound;
	
	public void setSoundsList(Map<String, Sound> s){
		sounds = s;
	}
	public void playSound(String soundKey) {
		if(soundPlaying == false) {
			Sound s = sounds.get(soundKey);
			long id = s.play(1.0f);
			
			this.currentSoundID = id;
			this.currentSound = s;
			
			s.setLooping(id, true);
			soundPlaying = true;
		}
	}
	public void stopAllSound() {
		if(soundPlaying == true) {
			this.currentSound.stop(this.currentSoundID);
			this.soundPlaying = false;
		}
		
	}
	
	public SoundManager() {
		soundPlaying = false;
	}
	
	
	
}
