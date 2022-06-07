package menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import utils.ImageLoader;
import com.Main;

public class Menu {
	
	public BufferedImage menuImage, startgametext, maintitle;
	ImageLoader il;
	
	public Menu() {
		il = new ImageLoader();
		try {
			menuImage = il.load("menuImage.jpg");
			startgametext = il.load("startgametext.png");
			maintitle = il.load("maintitle.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(menuImage, 0 ,0,  Main.WIDTH, Main.HEIGHT, null);
		g.drawImage(startgametext, 200, Main.HEIGHT*4/5, Main.WIDTH-400, Main.HEIGHT/9, null);
		g.drawImage(maintitle, 100, Main.HEIGHT/7, Main.WIDTH-200, Main.HEIGHT/6, null);
	}

}
