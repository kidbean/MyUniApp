package com.raycast.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.raycast.main.GamePanel;
import com.raycast.main.KeyHandler;
import com.raycast.map.MapLoader;
import com.raycast.map.Textures;

public class Player{
	
	KeyHandler kh;
	GamePanel gp;
	MapLoader mapLoad = new MapLoader();
	Textures tex = new Textures();
	
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
	
		if(kh.up) {
			if(mapLoad.map[ipy*mapLoad.mapX + ipx_add_xo] == 0) {
				px += pdx;
			}
			if(mapLoad.map[ipy_add_yo*mapLoad.mapX + ipx] == 0) {
				py += pdy;
			}
		}
		if(kh.down) {
			if(mapLoad.map[ipy*mapLoad.mapX + ipx_sub_xo] == 0) {
				px -= pdx;
			}
			if(mapLoad.map[ipy_sub_yo*mapLoad.mapX + ipx] == 0) {
				py -= pdy;
			}
		}
		else if(kh.left) {
			pa -= 0.1;
			if(pa < 0) { pa += 2*pi;}
			pdx = Math.cos(pa)*5;
			pdy = Math.sin(pa)*5;
		}
		else if(kh.right) {
			pa += 0.1;
			if(pa > 2*pi) { pa -= 2*pi;}
			pdx = Math.cos(pa)*5;
			pdy = Math.sin(pa)*5;
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
		
			float shade = 1.0f;
			
			if(disV<disH) {
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
			 g2.drawLine(px+4, py+4, (int)rx, (int)ry);
			
			
			//Draw 3D Walls
			float ca =(float) (pa-ra);
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
			
			float lineO = 160-lineH/2;
			float ty = ty_off*ty_step;
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
				float c = tex.AllTextures[(int) (ty) * 32 + (int)(tx)] * shade;
				g2.setStroke(new BasicStroke(8));
				g2.setColor(new Color(c, c, c));
				g2.drawLine((int)r*8 + 530, (int)lineO + y, (int)r*8 + 530, y + (int)lineO);
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

		
		//Draw Player and Line
		g2.setColor(Color.green);
		g2.fillRect(px, py, playerSize, playerSize);
		//g2.drawLine(px+4, py+4, (int)px+(int)pdx*5, (int)py+(int)pdy*5);

		
	}

}
