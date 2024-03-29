package com.mygdx.game;

public class Boss {

	private int globalBossHP = 100;
	
	public Boss() {
		
	}
	
	public int getGlobalBossHP() {
		return globalBossHP;
	}
	public void setGlobalBossHP(int newHP){
		globalBossHP = newHP;
	}
}
