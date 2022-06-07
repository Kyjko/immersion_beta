package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.Main;
import utils.ImageLoader;

public class Grid {

	int x = Main.WIDTH / 4;
	int y = Main.HEIGHT / 2;
	int width = Main.WIDTH / 2;
	int height = Main.HEIGHT / 4;
	int cellSize = 30;
	int capacityx, capacityy;
	int occupied = 0;
	
	ImageLoader loader;
	
	BufferedImage fish, gold, junk, tablet;
	
	public Item[][] items;

	public boolean active = false;

	public Grid() {
		loader= new ImageLoader();
		try {
			fish = loader.load("rottenfish_item.png");
			gold = loader.load("gold_item.png");
			junk = loader.load("junk_item.png");
			tablet = loader.load("tablet_item.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		capacityx = width / cellSize;
		capacityy = height / cellSize;
		items = new Item[capacityx][capacityy];
	}

	public void render(Graphics g) {
		if (active) {
	
		for(int i = 0; i < items.length; i++) {
			for(int j  =0; j < items[0].length; j++) {
				if(items[i][j] != null) {
					if(items[i][j].type == Type.Fish) {
						
						g.drawImage(fish, x + i*cellSize, y + j*cellSize, cellSize, cellSize, null);
					}
					if(items[i][j].type == Type.Junk) {
						
						g.drawImage(junk, x + i*cellSize, y + j*cellSize, cellSize, cellSize, null);
					}
					if(items[i][j].type == Type.Gold) {
						
						g.drawImage(gold, x + i*cellSize, y + j*cellSize, cellSize, cellSize, null);
					}
					if(items[i][j].type == Type.Tablet) {
						
						g.drawImage(tablet, x + i*cellSize, y + j*cellSize, cellSize, cellSize, null);
					}
				}
			}
		}
		
		for(int i = 0; i < items.length; i++) {
			for(int j  =0; j < items[0].length; j++) {
				if(items[i][j] !=null)occupied++;
			}
		}
		
		for (int i = 0; i < width / cellSize; i++) {
			for (int j = 0; j < height / cellSize; j++) {
				g.setColor(new Color(40, 40, 40, 80));
				g.fillRect(cellSize * i + x, cellSize * j + y, cellSize, cellSize);
				g.setColor(Color.black);
				g.drawRect(cellSize * i + x, cellSize * j + y, cellSize, cellSize);
			}
		}
		g.setColor(Color.white);
		g.drawString(String.valueOf(occupied) + "/" + String.valueOf(capacityy * capacityx), x + -20, y -20);
		
		}
		if(occupied >= 5) {
			Main.lada.active=false;
			
		}
		occupied=0;
		
	}
	
	public void add(Item item) {
		loop:
		for(int i = 0; i < items[0].length; i++) {
			for(int j = 0; j < items.length; j++) {
				if(items[j][i]==null) {
					items[j][i] = item;
					break loop;
				}
			}
		}
	}
}
