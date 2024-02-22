package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Configuration cfgDetails = new Configuration();
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setWindowedMode(cfgDetails.wWidth, cfgDetails.wHeight);
		config.setTitle(cfgDetails.gameTitle);
		
		config.setForegroundFPS(60);
		config.setTitle("GDXGame");
		
		Lwjgl3Application app = new Lwjgl3Application(new AmpEngine(), config);
		//get pid of app
		//psutil OS_SYSKILL_T check if pid is running
		//if pid not in, spawn new
		//if pid in, wait
		
		ApplicationListener ampEngine = app.getApplicationListener(); // Return AppEngine class object
		
//		app = new Lwjgl3Application(new AmpEngine(), config);
	}
}
