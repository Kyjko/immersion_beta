package com;

import java.awt.Graphics;
import java.util.Random;

public class Circle {

	float x, y, velx, vely;
	int id;
	static float terminal_vel=0.7f;
	int r = 50;

	float friction = 0.9999f;

	public Circle(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;

	}

	public void render(Graphics g) {

		// g.setColor(Color.yellow); g.drawOval((int)x, (int)y, r, r);

		// System.out.println(x + " " + y);

		g.drawImage(Main.bubble2, (int) x, (int) y, r, r, null);
		
		if(velx < 0 ) {
			if(Math.abs(velx) > terminal_vel)velx=-terminal_vel;
		}
		if(velx > 0 ) {
			if(Math.abs(velx) > terminal_vel)velx=terminal_vel;
		}
		if(vely < 0 ) {
			if(Math.abs(vely) > terminal_vel)vely=-terminal_vel;
		}
		if(vely > 0 ) {
			if(Math.abs(vely) > terminal_vel)vely=terminal_vel;
		}
		
		x += velx;
		y += vely;

		if (x < -r) {

			x = Main.WIDTH - r - 10;

			Main.imgnum--;
			Main.setupObstacles(1000, new Random());
			Main.initrays();
		}

		if (x > Main.WIDTH) {

			x = 10;

			Main.imgnum++;

			Main.setupObstacles(1000, new Random());
			Main.initrays();
		}

		if (y < 40) {
			vely *= 0;
		}

		if (y > Main.HEIGHT - r) {
			vely *= -0.5;
		}

		if ((Main.imgnum == 4 && x > Main.WIDTH - r) || (Main.imgnum == 0 && x < 0)) {
			velx *= -1;
		}

		if (Main.imgnum < 0) {
			Main.imgnum = 0;
		}
		if (Main.imgnum > 4) {
			Main.imgnum = 4;
		}

		if (y <= Main.HEIGHT - Main.waterlevel) {
			vely += 0.002f * 1300 / Main.fps;
			Main.outofwater = true;
		} else {
			Main.outofwater = false;
		}
		if (y >= Main.HEIGHT - Main.waterlevel) {
			velx *= friction;
			vely *= friction;
		}

	}
}
