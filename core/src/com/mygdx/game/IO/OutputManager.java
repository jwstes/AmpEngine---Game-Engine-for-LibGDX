package com.mygdx.game.IO;
import com.badlogic.gdx.audio.*;

import java.util.Map;


// OutputManager manages outputs (other than display) - as of now largely used for outputting audio. Implements
// Interface for sound methods.
public class OutputManager implements SoundInterface{

	// SoundInterface-relevant Properties
	private Map<String, Sound> sounds;
	private boolean soundPlaying;
	private long currentSoundID;
	private Sound currentSound;


	// OutputManager Constructor
	public OutputManager() {
		soundPlaying = false;
	}

	// Sound related Methods
	public void setSoundsList(Map<String, Sound> s) {
		sounds = s;
	}
	
	public void playSound(String soundKey) {
		if (!soundPlaying) {
			Sound s = sounds.get(soundKey);
			long id = s.play(1.0f);

			this.currentSoundID = id;
			this.currentSound = s;

			s.setLooping(id, true);
			soundPlaying = true;
		}
	}
	
	public void stopAllSound(){
		if(soundPlaying) {
			this.currentSound.stop(this.currentSoundID);
			this.soundPlaying = false;
		}
	}
	

	
}