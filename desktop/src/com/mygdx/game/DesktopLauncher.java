package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Configuration cfgDetails = new Configuration();
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setWindowedMode(cfgDetails.wWidth, cfgDetails.wHeight);
		config.setTitle("AmpEngine - Demo Game");
		
		config.setForegroundFPS(120);
		config.setTitle("GDXGame");
		new Lwjgl3Application(new AmpEngine(), config);
		config.setResizable(false);
	}
}
