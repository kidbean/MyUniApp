package com.quiz.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	final int WIDTH = 1280;
	final int HEIGHT = 960;
	
	JButton b[] = new JButton[10];

	public String buttonText;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GRAY);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		
		setUpQuiz();
		for(int i = 0; i < b.length; i++) {
			this.add(b[i]);
		}
		
		this.setFocusable(true);
	}
	
	public void setUpQuiz() {
		
		//make buttons
		for(int i = 0; i < b.length; i++) {
			b[i] = makeButton("Test", 50 + (i*100), 50, 100, 100 );
			
		}
		
		
		
	}
	
	public JButton makeButton(String s, int x, int y, int w, int h) {
		
		JButton btn = new JButton();
		
		btn.setText(s);
		btn.setBounds(x, y, w, h);
		
		return btn;
		
	}

}
