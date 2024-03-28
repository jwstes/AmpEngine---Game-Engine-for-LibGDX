package com.mygdx.game.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    private boolean isMuted = false;
    private Music backgroundMusic; // Add this for background music



    // Private constructor to prevent instantiation from outside
    private OutputManager() {
    	   soundPlaying = false;
           isMuted = false;
           // Initialize the background music
           backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("betterMusic.mp3"));
           backgroundMusic.setLooping(true);
           backgroundMusic.play();
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
        if (!isMuted && !soundPlaying) { // Check mute state
            Sound s = sounds.get(soundKey);
            if (s != null) {
                long id = s.play(1.0f); // Play with full volume
                this.currentSoundID = id;
                this.currentSound = s;
                s.setLooping(id, true);
                soundPlaying = true;
            }
        }
    }



    // Stop all sounds
    public void stopAllSound() {
        if (soundPlaying && currentSound != null) {
            this.currentSound.stop(this.currentSoundID);
            this.soundPlaying = false;
        }
    }
    
    
    
    
 // Method to mute all sounds
    public void muteAllSounds() {
        isMuted = true;
        // Logic to mute all sounds, e.g., set volume of all sounds to 0
    }
    
    // Method to unmute all sounds
    public void unmuteAllSounds() {
        isMuted = false;
        // Logic to unmute all sounds, e.g., restore volume of all sounds
    }
    
    // Check if sounds are muted
    public boolean isMuted() {
        return isMuted;
    }
    
    
    // Toggle mute state for both music and sound effects
    public void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            // Mute background music and all sounds
            if (backgroundMusic != null) backgroundMusic.setVolume(0);
            // Optionally mute other sounds here
        } else {
            // Unmute background music and all sounds
            if (backgroundMusic != null) backgroundMusic.setVolume(1); // Restore volume
            // Optionally unmute other sounds here
        }
    }
}