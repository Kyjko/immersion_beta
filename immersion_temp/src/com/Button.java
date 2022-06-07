package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {
	
	float x, y;
	int width, height;
	String value;
	
	public Button(float x, float y, String value) {
		this.x=x;
		this.y=y;
		this.value=value;
		
		width = 30;
		height=30;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int)x, (int)y, width, height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Lucida Console", Font.BOLD, 40));
		g.drawString(value, (int)x, (int)y+height-5);
	}
	
	public Rectangle bounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
