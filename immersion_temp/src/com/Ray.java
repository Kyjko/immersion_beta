package com;
import java.awt.Graphics;


public class Ray {

	boolean poutofwater = false;
	float x, y, velx, vely;
	int facex = 1, facey = 1;

	public Ray(float x, float y) {
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
		
		if(Main.imgnum == 0) {
			g.drawImage(Main.fish, (int) x, (int) y, 30 * facex, 30 * facey, null);
		}
		else if(Main.imgnum == 1) {
			g.drawImage(Main.fish2, (int) x, (int) y, 30 * facex, 30 * facey, null);
		} else if (Main.imgnum == 2) {
			g.drawImage(Main.fish3, (int) x, (int) y, 30 * facex, 30 * facey, null);
		}else if(Main.imgnum == 3) {
			g.drawImage(Main.fish4, (int) x, (int) y, 30 * facex, 30 * facey, null);
		} else if(Main.imgnum == 4) {
			g.drawImage(Main.fish5, (int) x, (int) y, 30 * facex, 30 * facey, null);
		}
		
		

		if (x >= Main.WIDTH - 30 || x <= 30)
			velx *= -1;
		if (y >= Main.HEIGHT - 70 || y <= 30)
			vely *= -1;

		x += velx;
		y += vely;

		if (x >= Main.c.x && x <= Main.c.x + Main.c.r && y >= Main.c.y && y <= Main.c.y + Main.c.r && !Main.gameover) {
			Main.rays.remove(this);
			Main.rays.add(new Ray(Main.r.nextInt(Main.WIDTH - 30), Main.r.nextInt(Main.HEIGHT - 150) + 150));
			Main.blur();
			
		}

		if (y <= Main.HEIGHT - Main.waterlevel) {
			vely += 0.0002f * 1300 / Main.fps;
			poutofwater = true;
		} else {
			poutofwater = false;
		}

	}
}
