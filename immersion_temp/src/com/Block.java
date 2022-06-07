package com;

import java.awt.Graphics;

public class Block {

	float x, y, velx, vely;
	float amp = 1;

	public Block(float x, float y) {
		this.x = x;
		this.y = y;

	}

	public void render(Graphics g) {
		g.drawImage(Main.bubble, (int) x, (int) y, 4, 4, null);
		// g.setColor(Color.white);
		// g.drawOval((int)x, (int)y, 4, 4);
		double a = Main.r.nextFloat() * Math.PI * 2;
		velx = (float) Math.sin(a) / 10 * amp;
		vely = (float) Math.cos(a) / 10 * amp;

		if (x > Main.c.x - 20 && x < Main.c.x + Main.c.r + 20 && y > Main.c.y - 20 && y < Main.c.y + Main.c.r + 20) {
			amp += 0.01f;

		} else {
			amp -= 0.01f;
		}

		if (amp < 1)
			amp = 1;
		if (amp > 6)
			amp = 6;

		x += velx;
		y += vely;
	}
}
