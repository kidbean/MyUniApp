package com.raycast.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.raycast.utils.Utils;

public class Ui {
	
	Utils util = new Utils();
	
	BufferedImage face = null;
	
	public Ui() {
		face = util.imageLoader("player\\tempFace");
	}
	
	public void draw(Graphics2D g2d) {
		//draw ui border
		g2d.setStroke(new BasicStroke(8));
		g2d.setColor(Color.black);
		g2d.drawRect(530, 347, 472, 145);
		
		//draw face
		g2d.drawImage(face, 525, 340, 196, 196, null);
		
	}
	
	public void update() {
		
	}

}
