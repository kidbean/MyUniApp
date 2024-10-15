package com.raycast.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.raycast.entity.Player;
import com.raycast.main.KeyHandler;
import com.raycast.utils.Utils;

public class MapLoader {
	
	Textures tex = new Textures();
	Utils utils = new Utils();
	public KeyHandler kh = new KeyHandler(null);
	public Player player;
	
	static double pi = 3.1415926535;
	static double p2 = pi/2;
	static double p3 = 3*pi/2;
	static double dr = 0.0174533;
	
	public final int mapX = 8, mapY = 8;
	public int mapW[] = {
			2, 2, 2, 2, 3, 2, 2, 2,
			2, 0, 0, 2, 0, 0, 0, 2, 
			3, 0, 0, 2, 0, 0, 0, 3,
			2, 2, 4, 2, 0, 0, 0, 2,
			2, 0, 0, 0, 0, 0, 0, 3,
			3, 0, 0, 0, 0, 0, 0, 2,
			2, 0, 0, 0, 0, 0, 0, 2,
			2, 2, 2, 2, 2, 2, 2, 2
	};
	public int mapF[] = {
			0, 0, 0, 0, 0, 0, 0, 0, 
			0, 1, 1, 0, 1, 1, 1, 0,
			0, 1, 1, 0, 1, 1, 1, 0,
			0, 0, 0, 0, 1, 1, 1, 0, 
			0, 1, 1, 1, 1, 1, 1, 0,
			0, 1, 1, 1, 1, 1, 1, 0, 
			0, 1, 1, 1, 1, 1, 1, 0,  
			0, 0, 0, 0, 0, 0, 0, 0
	};
	public int mapR[] = {
			0, 0, 0, 0, 0, 0, 0, 0, 
			0, 1, 1, 0, 1, 1, 1, 0,
			0, 1, 1, 0, 1, 1, 1, 0,
			0, 0, 0, 0, 1, 1, 1, 0, 
			0, 1, 1, 1, 1, 1, 1, 0,
			0, 1, 1, 1, 1, 1, 1, 0, 
			0, 1, 1, 1, 1, 1, 1, 0,  
			0, 0, 0, 0, 0, 0, 0, 0
	};
	
	
	public MapLoader(Player player) {
		this.player = player;
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g2) {
		
		int hmt = 0, vmt = 0;
		
		//Draw Rays for player
		int r, mx, my, mp, dof;
		double rx = 0, ry = 0, ra, xo = 0, yo = 0, distT = 0;
		ra = player.pa - dr*30;
		if(ra < 0) {
			ra += 2*pi;
		}
		if(ra > 2*pi) {
			ra -= 2*pi;
		}
		
		for(r = 0; r < 60; r++) {
			//Check horizontal lines
						
			double disH = 1000000, hx = player.px, hy = player.py;
			dof = 0;
			double aTan = -1/Math.tan(ra);
			if(ra > pi) {
				ry = ((player.py/64)*64)-0.0001;
				rx = (player.py-ry)*aTan+player.px;
				yo = -64;
				xo = -yo*aTan;
			}
			if(ra < pi) {
				ry = ((player.py/64)*64)+64;
				rx = (player.py-ry)*aTan+player.px;
				yo = 64;
				xo = -yo*aTan;
			}
			if(ra == 0 || ra == pi) {rx = player.px; ry = player.py; dof = 8;}
			while(dof < 8) {
				mx = (int) (rx/64);
				my = (int) (ry/64);
				mp = my*mapX+mx;
				if(mp > 0 && mp<mapX*mapY && mapW[mp] > 0) {
					hmt = mapW[mp] - 1;
					hx = rx;
					hy = ry;
					disH = utils.dist(player.px, player.py, hx, hy, ra);
					dof = 8; //hit wall
				}else {
					rx += xo;
					ry += yo;
					dof++;
				}
			}
			//Check vertical Lines
			double disV = 1000000, vx = player.px, vy = player.py;
			dof = 0;
			double nTan = -Math.tan(ra);
			if(ra > p2 && ra < p3) {
				rx = ((player.px/64)*64)-0.0001;
				ry = (player.px-rx)*nTan+player.py;
				xo = -64;
				yo = -xo*nTan;
			}
			if(ra < p2 || ra > p3) {
				rx = ((player.px/64)*64)+64;
				ry = (player.px-rx)*nTan+player.py;
				xo = 64;
				yo = -xo*nTan;
			}
			if(ra == 0 || ra == pi) {rx = player.px; ry = player.py; dof = 8;}
			while(dof < 8) {
				mx = (int) (rx/64);
				my = (int) (ry/64);
				mp = my*mapX+mx;
				if(mp > 0 && mp<mapX*mapY && mapW[mp] > 0) {
					vmt = mapW[mp] - 1;
					vx = rx;
					vy = ry;
					disV = utils.dist(player.px, player.py, vx, vy, ra);
					dof = 8; //hit wall
				}else {
					rx += xo;
					ry += yo;
					dof++;
				}
			}
		
			float shade = 1.0f;
			
			if(disV<disH) {
				hmt = vmt;
				rx = vx; 
				ry = vy;
				distT = disV;
				shade = 0.5f;
			}
			if(disH<disV) {
				rx = hx;
				ry = hy;
				distT = disH;
				shade = 1.0f;
			}
			
			 g2.setColor(Color.red);
			 g2.setStroke(new BasicStroke(3));
			 g2.drawLine(player.px+4, player.py+4, (int)rx, (int)ry);
			
			
			//Draw 3D Walls
			float ca = (float) (player.pa-ra);
			if(ca < 0) {
				ca += 2*pi;
			}
			if(ca > 2*pi) {
				ca -= 2*pi;
			}
			distT = distT*Math.cos(ca);
			float lineH = (float) ((64*320)/distT);
			float ty_step = 32.0f / (float)lineH;
			float ty_off = 0;
			if(lineH > 320) {
				ty_off = (float) ((float)(lineH-320.0) / 2.0);
				lineH = 320;
			}
			

			float lineO = 220 - lineH/2;
			float ty = ty_off*ty_step + hmt * 32;
			float tx;
			if(shade == 1) {
				 tx = (int)(rx/2.0) % 32;
					if(ra > 180) {
						tx = 31-tx;
					}
			}else {
				tx = (int)(ry/2.0) % 32;
				if(ra > 90 && ra < 270) {
					tx = 31-tx;
				}
			}
			
			for(int y = 0; y < lineH; y++) {
				float c = (tex.AllTextures[(int) (ty) * 32 + (int)(tx) ] * shade);
				g2.setStroke(new BasicStroke(8));
				g2.setColor(new Color(c, c, c));
				g2.drawLine((int)r*8 + 530, (int)lineO + y, (int)r*8 + 530, (int)lineO + y);
				ty += ty_step;
			}

			ra += dr;
			if(ra < 0) {
				ra += 2*pi;
			}
			if(ra > 2*pi) {
				ra -= 2*pi;
			}
		}		
	}
}
