package com.mygdx.game.IO;

import com.badlogic.gdx.audio.Sound;

import java.util.Map;

public class OutputManager implements SoundInterface {

    // Singleton instance
    private static OutputManager instance;

    // SoundInterface-relevant Properties
    private Map<String, Sound> sounds;
    private boolean soundPlaying;
    private long currentSoundID;
    private Sound currentSound;

    // Private constructor to prevent instantiation from outside
    private OutputManager() {
        soundPlaying = false;
    }

    // Static method to retrieve the Singleton instance
    public static OutputManager getInstance() {
        if (instance == null) {
            instance = new OutputManager();
        }
        return instance;
    }

    // Set sounds list
    public void setSoundsList(Map<String, Sound> s) {
        sounds = s;
    }

    // Play sound
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

    // Stop all sounds
    public void stopAllSound() {
        if (soundPlaying) {
            this.currentSound.stop(this.currentSoundID);
            this.soundPlaying = false;
        }
    }
}