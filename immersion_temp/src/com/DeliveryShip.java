package com;

import java.awt.Graphics;

public class DeliveryShip {
	
	float x, y;
	int width = 300;
	int height = 80;
	
	public DeliveryShip(float x, float y) {
		this.x=x;
		this.y=y;
	}
	
	public void render(Graphics g) {
		g.drawImage(Main.delivery, (int)x, (int)y,width, height, null);
	}

}
