package com;

import java.awt.Graphics;

public class Chest {
	private long internalTimer = System.nanoTime();
	float x, y, vely;
	int id;
	int width, height;
	public boolean isopen;
	public boolean active = true;
	
	public Chest(float x, float y, int id) {
		this.x=x;
		this.y=y;
		this.id=id;
		isopen=false;
		vely = -0.8f;
		width=40;
		height=40;
	}
	
	public void render(Graphics g) {
		if(id == Main.imgnum && active) {
			
			if(!isopen) {
				g.drawImage(Main.chest, (int)x, (int)y, width, height, null);
			}else {
				g.drawImage(Main.openchest, (int)x, (int)y, width, height, null);
			}
			
			
			if(y >= Main.HEIGHT-40) {
				y+=vely;
			}
		
		
		}
		
		long currentTime = System.nanoTime();
		if(currentTime - internalTimer >= 10000000000L) {
			//active=false;
		}
		
		
	}

}
