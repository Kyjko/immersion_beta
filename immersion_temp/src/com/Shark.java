package com;

import java.awt.Graphics;

public class Shark {
	
	boolean poutofwater = false;
	float x, y, velx, vely;
	int facex = 1, facey = 1;
	
	public Shark(float x, float y) {
		this.x = x;
		this.y = y;
		velx = (float) (Main.r.nextFloat() + 0.01) / 5;
		vely = (float) (Main.r.nextFloat() + 0.01) / 5;
	}
	
	public void render(Graphics g) {
		/*
		 * g.setColor(Color.orange); g.fillRect((int)x, (int)y, 10, 10);
		 */

		if (velx > 0)
			facex = 1;
		if (velx < 0)
			facex = -1;
		
		float nx = ((Main.c.x - x) / 2 + x);
		float ny = (Main.c.y - y) / 2 + y;
		
		velx = (-x + nx) / Main.fps * 2;
		vely = (-y + ny) / Main.fps * 2;
		
		if(facex == -1) {
			g.drawImage(Main.shark, (int) x + 100, (int) y, -100 , 50 , null);
		} else {
			g.drawImage(Main.shark, (int) x, (int) y, 100 , 50 , null);
		}

		if (x >= Main.WIDTH - 30 || x <= 30)
			velx *= -1;
		if (y >= Main.HEIGHT - 70 || y <= 30)
			vely *= -1;

		x += velx;
		y += vely;

		if (x >= Main.c.x && x <= Main.c.x + Main.c.r && y >= Main.c.y && y <= Main.c.y + Main.c.r) {
			//Main.blur();
			
			
		}

		if (y <= Main.HEIGHT - Main.waterlevel) {
			vely += 0.0002f * 1300 / Main.fps;
			poutofwater = true;
		} else {
			poutofwater = false;
		}

	}

}
