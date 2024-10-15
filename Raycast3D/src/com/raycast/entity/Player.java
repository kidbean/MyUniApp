package com.raycast.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.raycast.main.GamePanel;
import com.raycast.main.KeyHandler;
import com.raycast.map.MapLoader;
import com.raycast.utils.Utils;

public class Player extends Entity{
	
	KeyHandler kh;
	GamePanel gp;
	MapLoader mapLoad = new MapLoader(this);
	
	static double pi = 3.1415926535;
	
	public int px, py, spd = 5;
	public double pdx, pdy, pa;
	public String direction;
	public int playerSize = 8;
	public int lineXScale = 17;
	
	public Player(GamePanel gp, KeyHandler kh) {
		super();
		this.kh = kh;
		setName("Adventurer");
		setRaceVocation("Human", "Warrior");
		System.out.println(this.name + "\n" + this.race + " | " + this.vocation);
	}
	
	public void update() {
		
		//collision
		int xo = 0;
		if(pdx<0) {
			xo -= 20;
		}else {
			xo = 20;
		}
		int yo = 0;
		if(pdy<0) {
			yo -= 20;
		}else {
			yo = 20;
		}
		int ipx = px/64, ipx_add_xo = (px+xo)/64, ipx_sub_xo = (px-xo)/64;
		int ipy = py/64, ipy_add_yo = (py+yo)/64, ipy_sub_yo = (py-yo)/64;
	
		//movement
		if(kh.up) {
			if(mapLoad.mapW[ipy*mapLoad.mapX + ipx_add_xo] == 0) {
				px += pdx;
			}
			if(mapLoad.mapW[ipy_add_yo*mapLoad.mapX + ipx] == 0) {
				py += pdy;
			}
		}
		if(kh.down) {
			if(mapLoad.mapW[ipy*mapLoad.mapX + ipx_sub_xo] == 0) {
				px -= pdx;
			}
			if(mapLoad.mapW[ipy_sub_yo*mapLoad.mapX + ipx] == 0) {
				py -= pdy;
			}
		}
		if(kh.left) {
			pa -= 0.1;
			if(pa < 0) { pa += 2*pi;}
			pdx = Math.cos(pa)*5;
			pdy = Math.sin(pa)*5;
		}
		if(kh.right) {
			pa += 0.1;
			if(pa > 2*pi) { pa -= 2*pi;}
			pdx = Math.cos(pa)*5;
			pdy = Math.sin(pa)*5;
		}
	}
	
	public void draw(Graphics2D g2) {

		//mapLoad.draw(g2);
		
		//Draw Player and Line
		g2.setColor(Color.green);
		g2.fillRect(px, py, playerSize, playerSize);
		//g2.drawLine(px+4, py+4, (int)px+(int)pdx*5, (int)py+(int)pdy*5);

		
	}

}
