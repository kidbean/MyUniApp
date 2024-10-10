package com.quiz.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	final int WIDTH = 1280;
	final int HEIGHT = 960;
	
	Buttons btn = new Buttons(this);
	JButton b = new JButton();
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GRAY);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		
		setUpQuiz();
		
		this.setFocusable(true);
	}
	
	public void setUpQuiz() {
		btn.createButtons();
		
	}

}
