package com.quiz.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Buttons{
	
	GamePanel gp;
	
	public JButton b[] = new JButton[100];
	public String abc = "ABC";
	public String buttonText;
	//int i;
	
	public Buttons(GamePanel gp){
		this.gp = gp;
	}
	
	public void createButtons() {
		//make buttons
		for(int i = 0; i < abc.length(); i++) {
			char ch = abc.charAt(i);
			String s = ""+ch;
			b[i] = makeButton(s, 50 + (i*100), 50, 100, 100 );
			b[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(s);
				}
			});
			gp.add(b[i]);
		}
	}	
		
	public JButton makeButton(String s, int x, int y, int w, int h) {
			
		JButton btn = new JButton();
			
		btn.setText(s);
		btn.setBounds(x, y, w, h);
			
		return btn;
			
	}

}
