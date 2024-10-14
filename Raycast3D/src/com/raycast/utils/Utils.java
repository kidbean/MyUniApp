package com.raycast.utils;

public class Utils {
	
	//distance for raycast
	public double dist(double ax, double ay, double bx, double by, double ang) {
		return (Math.sqrt((bx-ax) * ( bx-ax) + (by-ay) * (by-ay)));
	}
	
	public void imageLoader(String filePath) {
		
	}

}
