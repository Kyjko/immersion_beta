package com;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DialogBox {
	String[]parts;
	public DialogBox(String message) {
		int count = 1;
		for(int i = 0; i < message.length(); i++) {
			if(message.charAt(i) == '*')count++;
		}
		parts = new String[count];
		for(int i = 0; i < parts.length; i++) {
			parts[i] = message.split("\\*")[i];
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(80, 80, 80, 80));
		g.fillRoundRect(Main.WIDTH/4, Main.HEIGHT-200, Main.WIDTH/2, 150, 20, 20);
		g.setColor(Color.white);
		g.setFont(new Font("Lucida Console", Font.BOLD, 17));
		
		for(int i = 0; i < parts.length; i++) {
			g.drawString(parts[i], Main.WIDTH/4 + 25, Main.HEIGHT-150+i*25);
		}
		
	}

}
