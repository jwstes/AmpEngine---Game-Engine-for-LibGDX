package com.mygdx.game;
import com.badlogic.gdx.audio.*;

import java.util.Map;

public interface SoundInterface {
	void setSoundsList(Map<String, Sound> s);
	void playSound(String soundKey);
	void stopAllSound();

}