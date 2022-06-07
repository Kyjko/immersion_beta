package com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import inventory.Grid;
import inventory.Item;
import inventory.Type;

import utils.ImageLoader;
import utils.Music;

import menu.Menu;

/**
 * 
 * <h1>Main (Driver code)</h1> This class contains all the essential parts of
 * the driver code.
 * <p>
 * <b>Includes: </b>Thread start(), stop() method with a <i>public static
 * boolean</i> running variable; Main render method, initializing BufferStrategy
 * and Graphics; Constructor of Main; Every important <i>public static
 * </i>variables. Misc. methods, mainly accessed statically.
 * 
 * @author Bognár Miklós
 * @since 0.1
 * 
 *
 */

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -1073426840429788647L;

	// TODO PUBLIC / STATIC VARIABLES

	public static final int WIDTH = 800, HEIGHT = 800;

	public static boolean running = false;
	public static boolean paused = false;
	public static boolean ismenu = true;

	// epoch
	long startTime = 0;

	Thread t;

	static long fps = 0;

	// hp
	public static float hp = 180;

	public static boolean gameover = false;

	// dayNight
	public static int dayNightShift = 250;

	// death
	public static int deathBlack = 0;

	// nonfluid physics
	static boolean outofwater = false;

	// classes
	public static Circle c = null;
	public static Shark s;
	public static Chest lada;
	public static DeliveryShip deliveryship;

	public static HUD hud;
	public static Menu menu;

	public static ImageLoader il;

	public static BufferedImage bubble, bubble2, bigbg, shark, horizon, yourdied, chest, openchest,
	delivery;
	public static BufferedImage fish, fish2, fish3, fish4, fish5;
	public static BufferedImage pausemenu, menublueprint;

	public static ArrayList<BufferedImage> bgs = new ArrayList<BufferedImage>();
	public static ArrayList<Ray> rays = new ArrayList<Ray>();
	public static ArrayList<DialogBox> dias = new ArrayList<DialogBox>();

	// dialogBoxes
	public static boolean dialogBoxTriggered = false;
	public static boolean rayDialogTriggered = false;
	public static boolean cinematicInProgress = false;
	
	Button propminus = new Button(WIDTH/8+70, HEIGHT*2/3+20+50, "-");
	Button propplus = new Button(WIDTH/8+70, HEIGHT*2/3+20, "+");
	
	Button refminus = new Button(WIDTH/3+40, HEIGHT*2/3+20+50, "-");
	Button refplus = new Button(WIDTH/3+40, HEIGHT*2/3+20, "+");
	
	Button storminus = new Button(WIDTH/2+40, HEIGHT*2/3+20+50, "-");
	Button storplus = new Button(WIDTH/2+40, HEIGHT*2/3+20, "+");
	
	
	JLabel fpslabel = new JLabel();
	
	//TODO MONEY (BALANCE)
	
	public static int balance = 0;

	ImageIcon exit = new ImageIcon(getClass().getResource("/res/graphics/exit.png"));

	public static int imgnum = 2;

	int upper = 3;

	public static Random r;

	// sinmath
	static int waterlevel = 620;
	float periodus = 20;
	float amp = 5;
	float phase = 0;
	float pvel = 0.01f;

	// offset for window
	float xoff, yoff;

	// damageblur
	static int dred = 0;

	// view
	static int viewrange = 200;

	public static ArrayList<Block> points = new ArrayList<Block>();

	// TODO INVENTORY CLASS
	Grid grid;
	
	//TODO CONTINOUS TIMER INIT
	Timer contDialogTimer = new Timer();
	TimerTask contDialog = new TimerTask() {

		@Override
		public void run() {
			cinematicInProgress=true;
			dias.clear();
			dias.add(new DialogBox("Welcome to IMMERSION, initiate!*I'm going to guide you thru*the mechanics and goals."));
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			dias.clear();
			dias.add(new DialogBox("You can move by dragging*the mouse. Interact with*left click."));
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			dias.clear();
			dias.add(new DialogBox("Your main goal is to retrieve*ancient artifacts contained in*small chests."));
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			dias.clear();
			dias.add(new DialogBox("You can buy upgrades for your*submarine. It costs you $.*You get $ from delivering goods."));
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			dias.clear();
			dias.add(new DialogBox("Avoid swimming entities, *they are likely to hurt you. Once*your health reaches 0, you die"));
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			dias.clear();
			cinematicInProgress = false;
			
		}
		
	};

	// TODO MAIN Constructor

	public Main() {
		r = new Random();

		il = new ImageLoader();

		try {
			bubble = il.load("bubble.png");
			bubble2 = il.load("bubble2.png");
			bigbg = il.load("bigbg.png");
			fish = il.load("fish.png");
			fish2 = il.load("fish2.png");
			fish3 = il.load("fish3.png");
			fish4 = il.load("fish4.png");
			fish5 = il.load("fish5.png");
			shark = il.load("shark.png");
			horizon = il.load("horizon.png");
			yourdied = il.load("yourdied.png");
			chest = il.load("chest.png");
			pausemenu = il.load("pausemenu.png");
			menublueprint = il.load("menublueprint.png");
			openchest = il.load("openchest.png");
			delivery = il.load("delivery.png");
		} catch (IOException e2) {
			// TODO if images arent loading -> catch these exceptions!!!
			e2.printStackTrace();
		}

		bgs.add(bigbg.getSubimage(0, 0, 800, 800));
		bgs.add(bigbg.getSubimage(800, 0, 800, 800));
		bgs.add(bigbg.getSubimage(1600, 0, 800, 800));
		bgs.add(bigbg.getSubimage(2400, 0, 800, 800));
		bgs.add(bigbg.getSubimage(3200, 0, 800, 800));

		c = new Circle(WIDTH / 2, HEIGHT / 2, 0);
		s = new Shark(700, 700);
		hud = new HUD(WIDTH / 4, 0);
		lada = new Chest(r.nextInt(WIDTH - 100) + 50, HEIGHT + 100, imgnum);
		grid = new Grid();
		menu = new Menu();
		deliveryship = new DeliveryShip(WIDTH - 400, HEIGHT-waterlevel-75);

		setupObstacles(1000, r);

		JFrame f = new JFrame("Immersion 0,1");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setSize(WIDTH, HEIGHT);
		f.setResizable(false);

		f.setUndecorated(true);
		f.setLocationRelativeTo(null);
		f.setShape(new RoundRectangle2D.Float(0, 0, 800, 800, 50, 50));

		f.setFocusable(true);
		f.requestFocus();

		try {
			f.setIconImage(il.load("frame_icon.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		f.setVisible(true);

		JPanel ui = new JPanel();
		ui.setSize(800, 40);
		ui.setBackground(Color.black);

		ui.add(fpslabel);

		Image eimage = exit.getImage();
		Image newimage = eimage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		exit = new ImageIcon(newimage);

		JLabel exitLabel = new JLabel();
		exitLabel.setSize(40, 40);
		exitLabel.setIcon(exit);
		exitLabel.setLocation(750, 0);

		f.add(exitLabel);
		f.add(ui);
		f.add(this);

		this.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {

				int c = e.getKeyCode();
				if (c == KeyEvent.VK_ESCAPE && (dialogBoxTriggered || rayDialogTriggered)) {
					dias.remove(0);
					dialogBoxTriggered = false;
					rayDialogTriggered = false;
				}
				if (c == KeyEvent.VK_SPACE && gameover) {
					restart();
				} else if (c == KeyEvent.VK_SPACE && !gameover && !paused) {
					paused = true;
				} else if (c == KeyEvent.VK_SPACE && !gameover && paused) {
					paused = false;
				}

				if (c == KeyEvent.VK_E && !gameover && !grid.active) {
					grid.active = true;
				} else if (c == KeyEvent.VK_E && !gameover && grid.active) {
					grid.active = false;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		});

		ui.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getX() >= 750 && e.getY() <= 40) {
					WindowEvent we = new WindowEvent(f, WindowEvent.WINDOW_CLOSING);
					Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(we);

					f.setVisible(false);
					// f.dispose();

					System.exit(0);
				}

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		ui.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

				f.setLocation(e.getXOnScreen() - WIDTH / 2, e.getYOnScreen());
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e) && !outofwater && !gameover && !paused && !ismenu) {

					float nx = ((c.x - e.getX()) / 2 + e.getX());
					float ny = (c.y - e.getY()) / 2 + e.getY();

					c.velx = (-c.x + nx) / fps * 2;
					c.vely = (-c.y + ny) / fps * 2;

				}

			}

			public void mouseMoved(MouseEvent e) {
				
			}

		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override

			// TODO DIALOG BOX + INTERACTIONS

			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) && e.getX() >= s.x && e.getX() <= s.x + 100 && e.getY() >= s.y
						&& e.getY() <= s.y + 50 && !dialogBoxTriggered && !rayDialogTriggered && !paused && !ismenu && !cinematicInProgress) {
					dias.add(new DialogBox("Hi, I'm Bob. nice to meet you.*Press E to access inventory*Press SPACE to open console"));
					dialogBoxTriggered = true;
				}
				for (int i = 0; i < rays.size(); i++) {
					Ray r = rays.get(i);
					if (SwingUtilities.isRightMouseButton(e) && e.getX() >= r.x && e.getX() <= r.x + 50
							&& e.getY() >= r.y && e.getY() <= r.y + 50 && !rayDialogTriggered && !dialogBoxTriggered
							&& !paused && !ismenu && !cinematicInProgress) {

						dias.add(new DialogBox("These guys are likely to hurt you.*Press E to access inventory*Press SPACE to open console"));
						rayDialogTriggered = true;
					}
				}
				// TODO INVENTORY ACTIONS
				if (SwingUtilities.isRightMouseButton(e) && e.getX() > lada.x && e.getX() < lada.x + lada.width
						&& e.getY() > lada.y && e.getY() < lada.y + lada.height
						&& ((c.x - lada.x) * (c.x - lada.x) + (c.y - lada.y) * (c.y - lada.y) <= 10000) && !paused
						&& !ismenu && lada.active) {
					lada.isopen=true;
					int p = r.nextInt(100);
					if (p < 33) {
						grid.add(new Item(Type.Fish));
					} else if (p > 32 && p < 90) {
						grid.add(new Item(Type.Junk));
					} else if (p > 89 && p < 95) {
						grid.add(new Item(Type.Gold));
					} else if(p > 94) {
						grid.add(new Item(Type.Tablet));
						dias.clear();
						
						dias.add(new DialogBox("WOw! You just found one of*the oldest relics of all*time! An original android tablet.*You should check the console now..."));
						
					}
				}
				if (SwingUtilities.isLeftMouseButton(e) && ismenu) {
					
					ismenu = false;
					contDialogTimer.schedule(contDialog, 1000);
				}
				
				if((SwingUtilities.isRightMouseButton(e) && e.getX() > deliveryship.x && e.getX() < deliveryship.x + deliveryship.width
						&& e.getY() > deliveryship.y && e.getY() < deliveryship.y + deliveryship.height
						&& ((c.x - deliveryship.x) * (c.x - deliveryship.x) + (c.y - deliveryship.y) * (c.y - deliveryship.y) <= 25000) && !paused
						&& !ismenu)){
					for(int i = 0; i < grid.items.length; i++) {
						for(int j = 0; j < grid.items[0].length; j++) {
							if(grid.items[i][j] != null) {
								balance+=(grid.items[i][j].value);
								grid.items[i][j] = null;
							}
						}
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		
		//TODO CONTINOUS DIALOG BOXES
		

		start();

		initrays();

	}

	public static void initrays() {
		// TODO INITRAYS
		rays.clear();
		for (int i = 0; i < 5; i++) {
			rays.add(new Ray(r.nextInt(WIDTH - 30), r.nextInt(HEIGHT - 150) + 150));
		}
	}

	// TODO MISC

	public static void setupObstacles(int n, Random r) {
		points.clear();
		for (int i = 0; i < n; i++) {
			points.add(new Block(r.nextInt(WIDTH), r.nextInt(HEIGHT - 150) + 250));
		}
	}

	double h = 0;
	// color for water
	Color tc = new Color(0, 100, 200, 60);

	// TODO GETTING DAMAGE
	static void blur() {
		Timer tmr = new Timer();
		TimerTask dmg = new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					hp--;
					dred++;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}
		};

		tmr.schedule(dmg, 0);

	}

	// TODO MAIN RENDER
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		if (!paused && !ismenu) {

			g.drawImage(bgs.get(imgnum), (int) 0, (int) 0, WIDTH, HEIGHT, null);

			// horizont
			g.drawImage(horizon, 0, 50, WIDTH, 150, null);
			
			deliveryship.render(g);
			c.render(g);
			// ripple

			for (Block b : points) {
				b.render(g);
			}

			// waves
			g.setColor(tc);
			for (int i = 0; i < WIDTH; i++) {
				h = waterlevel + Math.sin(i / periodus + phase) * amp;
				g.fillRect(i, HEIGHT, 1, (int) -h);
			}

			if (periodus > 45 || periodus < 20)
				pvel *= -1;

			periodus += pvel;

			// rays
			for (int i = 0; i < rays.size(); i++) {
				rays.get(i).render(g);
			}
			lada.render(g);
			s.render(g);

			// damageblur
			g.setColor(new Color(255, 0, 0, dred / 2));
			g.fillRect(0, 0, WIDTH, HEIGHT);

			if (hp == 0)
				gameover();

			float[] dist = { 0.5f, 0.7f, 1.0f };
			Color[] colors = { new Color(150, 150, 150, 20), new Color(60, 60, 60, 130),
					new Color(0, 0, 0, dayNightShift) };

			RadialGradientPaint p = new RadialGradientPaint(c.x + c.r, c.y + c.r, viewrange, c.x + c.r, c.y + c.r, dist,
					colors, CycleMethod.NO_CYCLE);

			g2d.setPaint(p);
			g2d.fillOval((int) c.x - WIDTH * 2 + c.r, (int) c.y - WIDTH * 2 + c.r, WIDTH * 4, WIDTH * 4);

			hud.render(g);

			g.setColor(new Color(0, 0, 0, deathBlack));
			g.fillRect(0, 0, WIDTH, HEIGHT);

			// inverntory rendering
			grid.render(g);

			if (!gameover) {
				for (DialogBox db : dias) {
					db.render(g);
				}
			}

			if (gameover) {
				g.drawImage(yourdied, WIDTH / 7, HEIGHT / 3, WIDTH * 3 / 4, HEIGHT / 3, null);
			}

		} else if (paused && !ismenu) {
			g.drawImage(pausemenu, 0, 0, WIDTH, HEIGHT, null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Lucida Console", Font.BOLD, 40));
			g.drawString("Your submarine", 250, 80);
			g.drawImage(menublueprint, 25, HEIGHT / 6, WIDTH - 50, HEIGHT * 4 / 6, null);
			
			propminus.render(g);
			propplus.render(g);
			refplus.render(g);
			refminus.render(g);
			storplus.render(g);
			storminus.render(g);
			
			g.setColor(Color.green);
			g2d.draw(propplus.bounds());
			g2d.draw(storplus.bounds());
			g2d.draw(refplus.bounds());
			g.setColor(Color.red);
			g2d.draw(propminus.bounds());
			g2d.draw(storminus.bounds());
			g2d.draw(refminus.bounds());
			
		} else if (ismenu) {
			menu.render(g);
		}

		bs.show();
		g.dispose();
	}

	// TODO DAY
	public void day_night() {
		Timer tmr = new Timer();
		TimerTask day = new TimerTask() {
			@Override
			public void run() {

				while (running) {

					for (int i = 130; i < 251; i++) {
						dayNightShift = i;

						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}
					
					
					

					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}

					for (int i = 250; i > 129; i--) {
						dayNightShift = i;

						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}
					
					lada.x = r.nextInt(WIDTH-200)+100;
					lada.y = HEIGHT+100;
					lada.active=true;
					lada.isopen=false;
					
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}

				}
			}
		};

		tmr.schedule(day, 0);
	}

	// TODO RESTART AND GAMEOVER

	public void restart() {
		dred = 0;
		viewrange = 200;
		hp = 180;
		gameover = false;
		deathBlack = 0;

		c.velx = 0;
		c.vely = 0;
		s.velx = 0;
		s.vely = 0;

		imgnum = 2;

		s.x = 700;
		s.y = 700;
		c.x = WIDTH / 2;
		c.y = HEIGHT / 2;
		
		lada.isopen=false;
		balance = 0;

		try {
			Music.playSound("dark.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			
			e.printStackTrace();
		}
	}

	public void gameover() {
		hp = -1;
		Timer t = new Timer();
		grid.active = false;

		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				for (int i = 0; i < 255; i++) {
					deathBlack = i;

					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

		};

		try {
			Music.getMusic().stop();
		} catch (Exception ex) {
		}

		t.schedule(tt, 0);

		for (int i = 0; i < grid.items.length; i++) {
			for (int j = 0; j < grid.items[0].length; j++) {
				grid.items[i][j] = null;
			}
		}

		gameover = true;
	}

	// TODO Thread operations

	public synchronized void stop() {
		try {
			t.join();
		} catch (InterruptedException ex) {
			System.err.println("Error while shutting " + t.getName() + " down.");
		}

		running = false;
	}

	public synchronized void start() {
		t = new Thread(this);
		t.start();
		running = true;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});

	}

	public void run() {
		startTime = System.nanoTime();
		System.out.println("Thread @" + t.getName() + " successfully started and now operating...");
		
		

		// init Music
		Timer legalja = new Timer();
		TimerTask looper = new TimerTask() {

			@Override
			public void run() {
				while (running) {
					try {
						Music.playSound("dark.wav");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

						e.printStackTrace();
					}
					try {
						Thread.sleep(55000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					Music.getMusic().stop();
				}
			}
		};

		legalja.schedule(looper, 0);

		day_night();

		while (running) {
			long start = System.nanoTime();

			render();

			fps = 1000000000 / (System.nanoTime() - start);
			fpslabel.setText("Immersion 0.1 - " + fps);

		}

	}

}
