package com.raycast.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.raycast.entity.Player;
import com.raycast.map.MapLoader;

public class GamePanel extends JPanel implements Runnable {
	
	MapLoader mapLoad = new MapLoader();

	final int originalTileSize = 32;
	final int scale = 2;

	public final int tileSize = originalTileSize * scale;
	public final int SCREEN_WIDTH = 1024;
	public final int SCREEN_HEIGHT = 512;

	int FPS = 60;
	public long drawCount;

	public KeyHandler kh = new KeyHandler(this);

	public Player player = new Player(this, kh);

	Thread gameThread;

	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(100, 100, 100));
		this.setDoubleBuffered(true);

		this.addKeyListener(kh);
		this.setFocusable(true);
	}

	public void setUpGame() {

		Player.px = 300;
		Player.py = 300;
		Player.pdx = Math.cos(Player.pa)*5;
		Player.pdy = Math.sin(Player.pa)*5;

	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {

				update();
				repaint();

				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}

		}

	}

	public void update() {

		player.update();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int x, y, xo, yo;
		for (y = 0; y < mapLoad.mapY; y++) {
			for (x = 0; x < mapLoad.mapX; x++) {
				if(mapLoad.map[y*mapLoad.mapX+x] >= 1) { 
					g.setColor(Color.white);
				}else {
					g.setColor(Color.black);
				}
				xo = x*tileSize; yo = y*tileSize;
				g.fillRect(xo, yo, tileSize-1, tileSize-1);
			}
		}
		player.draw(g2);

		g2.dispose();
	}

}