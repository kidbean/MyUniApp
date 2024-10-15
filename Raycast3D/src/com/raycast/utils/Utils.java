package com.raycast.utils;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {
	
	BufferedImage image;
	
	//distance for raycast
	public double dist(double ax, double ay, double bx, double by, double ang) {
		return (Math.sqrt((bx-ax) * ( bx-ax) + (by-ay) * (by-ay)));
	}
	
	public BufferedImage imageLoader(String filePath) {
		try {
			image = ImageIO.read(new File(filePath));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}

}
