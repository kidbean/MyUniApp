package com.raycast.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.raycast.main.GamePanel;
import com.raycast.main.KeyHandler;
import com.raycast.map.MapLoader;

public class Player{
	
	KeyHandler kh;
	GamePanel gp;
	MapLoader mapLoad = new MapLoader();
	
	static double pi = 3.1415926535;
	static double p2 = pi/2;
	static double p3 = 3*pi/2;
	static double dr = 0.0174533;
	
	
	public static int px, py, spd = 5;
	public static double pdx, pdy, pa;
	public String direction;
	public int playerSize = 8;
	
	public int lineXScale = 17;
	
	public Player(GamePanel gp, KeyHandler kh) {
		super();
		
		this.kh = kh;
	}
	
	public void update() {
		if(kh.up || kh.down || kh.left || kh.right) {
			if(kh.up == true) {
				py += pdy; 
				px += pdx;
			}
			else if(kh.down == true) {
				py -= pdy; 
				px -= pdx;
			}
			else if(kh.left == true) {
				pa -= 0.1;
				if(pa < 0) { pa += 2*pi;}
				pdx = Math.cos(pa)*5;
				pdy = Math.sin(pa)*5;
			}
			else if(kh.right == true) {
				pa += 0.1;
				if(pa > 2*pi) { pa -= 2*pi;}
				pdx = Math.cos(pa)*5;
				pdy = Math.sin(pa)*5;
			}
		}
	}
	
	public double dist(double ax, double ay, double bx, double by, double ang) {
		
		return (Math.sqrt((bx-ax) * ( bx-ax) + (by-ay) * (by-ay)));
	}
	
	
	public void draw(Graphics2D g2) {
		
		//Draw Rays for player
		
		int r, mx, my, mp, dof;
		double rx = 0, ry = 0, ra, xo = 0, yo = 0, distT = 0;
		ra = pa - dr*30;
		if(ra < 0) {
			ra += 2*pi;
		}
		if(ra > 2*pi) {
			ra -= 2*pi;
		}
		
		for(r = 0; r < 60; r++) {
			//Check horizontal lines
						
			double disH = 1000000, hx = px, hy = py;
			dof = 0;
			double aTan = -1/Math.tan(ra);
			if(ra > pi) {
				ry = ((py/64)*64)-0.0001;
				rx = (py-ry)*aTan+px;
				yo = -64;
				xo = -yo*aTan;
			}
			if(ra < pi) {
				ry = ((py/64)*64)+64;
				rx = (py-ry)*aTan+px;
				yo = 64;
				xo = -yo*aTan;
			}
			if(ra == 0 || ra == pi) {rx = px; ry = py; dof = 8;}
			while(dof < 8) {
				mx = (int) (rx/64);
				my = (int) (ry/64);
				mp = my*mapLoad.mapX+mx;
				if(mp > 0 && mp<mapLoad.mapX*mapLoad.mapY && mapLoad.map[mp] == 1) {
					hx = rx;
					hy = ry;
					disH = dist(px, py, hx, hy, ra);
					dof = 8; //hit wall
					
				}else {
					rx += xo;
					ry += yo;
					dof++;
				}
			}
			//Check Vertical Lines
			double disV = 1000000, vx = px, vy = py;
			dof = 0;
			double nTan = -Math.tan(ra);
			if(ra > p2 && ra < p3) {
				rx = ((px/64)*64)-0.0001;
				ry = (px-rx)*nTan+py;
				xo = -64;
				yo = -xo*nTan;
			}
			if(ra < p2 || ra > p3) {
				rx = ((px/64)*64)+64;
				ry = (px-rx)*nTan+py;
				xo = 64;
				yo = -xo*nTan;
			}
			if(ra == 0 || ra == pi) {rx = px; ry = py; dof = 8;}
			while(dof < 8) {
				mx = (int) (rx/64);
				my = (int) (ry/64);
				mp = my*mapLoad.mapX+mx;
				if(mp > 0 && mp<mapLoad.mapX*mapLoad.mapY && mapLoad.map[mp] == 1) {
					vx = rx;
					vy = ry;
					disV = dist(px, py, vx, vy, ra);
					dof = 8; //hit wall
					
				}else {
					rx += xo;
					ry += yo;
					dof++;
				}
			}
			
			if(disV<disH) {
				rx = vx; 
				ry = vy;
				distT = disV;
				g2.setColor(new Color(105, 105, 105));
			}
			if(disH<disV) {
				rx = hx;
				ry = hy;
				distT = disH;
				g2.setColor(new Color(128, 128, 128));
			}
			
			//g2.setColor(Color.red);
			 //g2.setStroke(new BasicStroke(3));
			 //g2.drawLine(px+4, py+4, (int)rx, (int)ry);
			
			
			//Draw 3D Walls
			float ca =(float) (pa-ra);
			if(ca < 0) {
				ca += 2*pi;
			}
			if(ca > 2*pi) {
				ca -= 2*pi;
			}
			distT = distT*Math.cos(ca);
			float lineH = (float) ((64*640)/distT);
			if(lineH > 640) {
				lineH = 640;
			}
			float lineO = 300-lineH/2;
			g2.setStroke(new BasicStroke(20));
			
			g2.drawLine((int)r*lineXScale, (int)lineO, (int)r*lineXScale, (int)lineH + (int)lineO);
			
			ra += dr;
			if(ra < 0) {
				ra += 2*pi;
			}
			if(ra > 2*pi) {
				ra -= 2*pi;
			}
		}

		
		//Draw Player and Line
		//g2.setColor(Color.green);
		//g2.fillRect(px, py, playerSize, playerSize);
		//g2.drawLine(px+4, py+4, (int)px+(int)pdx*5, (int)py+(int)pdy*5);

		
	}

}
