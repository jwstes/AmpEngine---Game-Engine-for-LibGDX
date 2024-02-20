package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import java.util.HashMap;
import java.util.Map;

public class OutputManager{
	private SoundManager soundManager;
	
	public void setSoundList(Map<String, Sound> soundList) {
		soundManager.setSoundsList(soundList);
	}
	
	public void playSound(String soundKey) {
		soundManager.playSound(soundKey);
	}
	
	public void stopAllSound() {
		soundManager.stopAllSound();
	}
	
	public OutputManager() {
		soundManager = new SoundManager();
	}
	
}
