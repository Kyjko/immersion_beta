package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
	
	float x, y;
	float width;
	
	public HUD(float x, float y) {
		this.x=x;
		this.y=y;
		
		width = Main.WIDTH*3/4;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, (int)y, Main.WIDTH, 100);
		for(int i = 0; i <= Main.hp; i++) {
			g.setColor(new Color(180-i, i, 0));
			g.fillOval((int)x/2 + i*(int)width/180, (int)y, (int)width/180, 70);
		}
		g.setFont(new Font("Impact", Font.PLAIN, 25));
		g.drawString(String.valueOf((int)(Main.hp/180 * 100)) + "%", Main.WIDTH/2-10, 100);
		g.setColor(Color.orange);
		g.drawString(String.valueOf(Main.balance) + "$", Main.WIDTH/8-80, 70);
	}

}
