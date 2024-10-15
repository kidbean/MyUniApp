package com.raycast.entity;

public class Entity {
	
	int health;
	int x, y, speed;
	int strength, dexterity, constitution, intellect;
	int defence;
	
	int level;
	
	String race;
	String vocation;
	String name;
	
	public void setXYSpeed(int xLoc, int yLoc, int s) {
		x = xLoc; y = yLoc;
		speed = s;
	}
	
	public void setRaceVocation(String r, String v) {
		race = r;
		vocation = v;
	}

	public void setStats(int h, int s, int d, int c, int i, int def) {
		strength = s; dexterity = d; constitution = c; intellect = i;
		defence = (int) (def + (strength/4 * 1.5));
		health = h;
	}
	
	public void updateStats(int h, int s, int d, int c, int i ,int def) {
		
	}
	
	public void setName(String n) {
		name = n;
	}

}
