
package org.crusty.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.crusty.engine.particle.ParticleManager;

public abstract class CrustyEngine extends Canvas implements MouseListener, MouseMotionListener, KeyListener {
	
	/** Random serialVersionUID */
	private static final long serialVersionUID = -264862915258733931L;
	
	/* Various Managers */
	
	/** Particle Manager */
//	private ParticleManager pm = new ParticleManager();
	
	protected Screen currentScreen = null;
	
//	private DepthComparator depthComparator = new DepthComparator();
	
	public static boolean[] keys;
	
	/* GRAPHICS VARIABLES */
	
	private BufferStrategy strategy;
	private long lastTime;
	private long dt;
	private long accumulator;
	private long deltaTime = 10000000; // 1 / (10 000 000 / 1 000 000 000) = 100 FPS;#
	public static long timePassed = 0;
	private GraphicsConfiguration gc;
	
	private static float fps;
	
	public static int width, height;
	
	private static int timeMultiplier = 1;
	protected static long currentTime = 0;
	
	static boolean notifyUpdateScreen = false;
	
	public static ParticleManager particleManager;
	
	JFrame container;
	
	/**
	 * Crusty Engine
	 * Set currentScreen in constructor
	 * @param width
	 * @param height
	 * @param title
	 */
	public CrustyEngine(int width, int height, String title) {
		CrustyEngine.width = width;
		CrustyEngine.height = height;
		
		particleManager = new ParticleManager();
		
		loadImages();
		init(width, height, title);
	}
	
	public void init(int width, int height, String title) {
		
		/* Init Variables */
		
		keys = new boolean[255];
//		entities = new PriorityQueue<RootEntity>(10, depthComparator);
		
		/* Set up window */
		
		container = new JFrame(title);
		
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
//		setBounds(0, 0, width, height);
		setSize(new Dimension(width, height));
		panel.add(this);
		
		setIgnoreRepaint(true);
		
		container.pack();
//		container.setPreferredSize(new Dimension(width, height));
		container.setResizable(false);
		container.setVisible(true);
		
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		requestFocus();
		
		/* Graphics Setup */
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    gc = gd.getDefaultConfiguration();
	}
	
	public void setCurrentScreen(Screen s) {
		if (s != null)
			currentScreen = s;
		else {
			System.out.println("Trying to set currentScreen as null.");
		}
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	public static ParticleManager getParticleManager() {
		return particleManager;
	}
	
	public static long currentTimeMillis() {
		return currentTime;
	}
	
	public static void setTimeMultiplier(int mult) {
		timeMultiplier = mult;
	}
	
	public static int getTimeMultiplier() {
		return timeMultiplier;
	}
	
	/**
	 * //	String[] images = { "rocket.png",<br>
//	"rocketBottom.png",<br>
//	"rocketCapsule.png",<br>
//	"rocketMiddle.png",<br>
//	"stationalpha.png"};<br>
//  for (String s : images) {<br>
//  	sm.addSprite(s);<br>
//  }
	 */
	public abstract void loadImages();
	
	/** Override and call super.draw() */
	public void draw(Graphics2D g) {
		currentScreen.draw(g);
	}
	
	public void gameLoop() {
		
		boolean running = true;
		boolean firstTime = true;
		
		// Create off-screen drawing surface
	    BufferedImage bi = gc.createCompatibleImage(width, height);
	    
	    Graphics2D g = null;
	    Graphics graphics = null;
	    
		while (running) {
			long now = System.nanoTime();
			dt = now - lastTime;
			fps = 1000000000/dt;
			lastTime = now;
			
			accumulator += dt;
			
			if (firstTime) {
				accumulator = 0;
				firstTime = false;
			}
			while (accumulator >= deltaTime) {
				timePassed += deltaTime;
				
				for (int i = 0; i < timeMultiplier; i++) {
					logic(deltaTime);
					currentTime += deltaTime/1000000;
				}
//				System.out.println("curentTime: " + currentTime);
				accumulator -= deltaTime;
			}
			
			do {
				do {
					g = bi.createGraphics();
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							RenderingHints.VALUE_ANTIALIAS_ON);
					
					draw(g);
					
		//			// Draw particles
		//			pm.draw(g);
					
					// Draw FPS
					g.setFont(FontStore.smallFont);
					g.setColor(Color.WHITE);
					g.drawString("FPS: " + getFps(), 15, 20);
					
					// Blit image and flip...
					graphics = strategy.getDrawGraphics();
					graphics.drawImage(bi, 0, 0, null);
					
				} while (strategy.contentsRestored());
					
				strategy.show();
				
				Thread.yield();
				if (graphics != null) 
					graphics.dispose();
				if (g != null)
					g.dispose();
				
			} while (strategy.contentsLost());
		}
	}
	
//	/** AddAll entities to main entity list */
//	public void addEntities(PriorityQueue<RootEntity> entities) {
//		this.entities.addAll(entities);
//	}
	
//	/** RemoveAll entities from main entity list */
//	public void removeEntities(PriorityQueue<RootEntity> entities) {
//		this.entities.removeAll(entities);
//	}
	
	/** Override me and make sure you call super.logic(dt); */
	public void logic(long dt) {
		
//		if (currentScreen == null) {
//			
//		}
		
		currentScreen.logic(dt);
		
//		pm.update(dt);
//		Iterator<RootEntity> it = entities.iterator();
//		while (it.hasNext()) {
//			it.next().logic(dt);
//		}
	}

	public static float getFps() {
		return fps;
	}

//	private class KeyInputHandler extends KeyAdapter {
//		public void keyPressed(KeyEvent e) {
//			if (e.getKeyCode() < 255) {
//				keys[e.getKeyCode()] = true;
//			}
//		}
//
//		public void keyReleased(KeyEvent e) {
//			if (e.getKeyCode() < 255) {
//				keys[e.getKeyCode()] = false;
//			}
//		}
//	}
	
//	public void keyTyped(KeyEvent e) {
//		System.out.println("CrustyEngine keyChar: " + e.getKeyChar());
//	}

	public void keyPressed(KeyEvent e) {
//		System.out.println("KeyPressed: " + e.getKeyCode());
		if (e.getKeyCode() < 255) {
			keys[e.getKeyCode()] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 255) {
			keys[e.getKeyCode()] = false;
		}
	}
	
}
