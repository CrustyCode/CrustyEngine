package org.crusty.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

import org.crusty.engine.entity.RootEntity;
import org.crusty.util.Line;

public abstract class Screen {

	private DepthComparator depthComparator = new DepthComparator();
	
	/** Entities in Screen */
	protected PriorityBlockingQueue<RootEntity> entities;
	
	private CrustyEngine engine;
	
	private EventSystem eventSystem;
	
	public Screen(CrustyEngine engine) {
		// Load Entities
		entities = new PriorityBlockingQueue<RootEntity>(10, depthComparator);
		this.engine = engine;
		eventSystem = new EventSystem();
	}

	public PriorityBlockingQueue<RootEntity> getEntities() {
		return entities;
	}

	public CrustyEngine getEngine() {
		return engine;
	}

	/** Add entity to screen */
	public void addEntity(RootEntity e) {
		getEntities().add(e);
	}
	
	public void setCurrentScreen(Screen s) {
		engine.setCurrentScreen(s);
	}
	
	public void notifyRemoval(RootEntity e) {
		getEntities().remove(e);
		// Get rid of it!
		e = null;
	}
	
	/** Override and call super.draw() to draw entities */
	public void draw(Graphics2D g) {
		//	Draw Entities
		Object[] array = getEntities().toArray();
		RootEntity[] arr = new RootEntity[array.length];
		for (int i = 0; i < array.length; i++) {
			arr[i] = (RootEntity) array[i];
		}
		Arrays.sort(arr); // TODO Hack, not efficient, 
		for (int i = 0; i < arr.length; i++) {
			arr[i].draw(g);
		}
		CrustyEngine.particleManager.draw(g);
		// replace Priority Blocking queue with something that reliably sorts
//		Iterator<RootEntity> it = getEntities().iterator();
//		Iterator<RootEntity> it = arr
//		while (it.hasNext()) {
//			it.next().draw(g);
//		}
		
	}
	
	/** Override and call super.logic() */
	public void logic(long dt) {
		CrustyEngine.particleManager.logic(dt);
//		Logic Entities
		Iterator<RootEntity> it = getEntities().iterator();
		while (it.hasNext()) {
			it.next().logic(dt);
		}
		
		// Collisions
		for (RootEntity e1 : getEntities()) {
			for (RootEntity e2 : getEntities()) {
				if (e1.checkCollisions && e2.checkCollisions) {
//					Object shape1 = e1.getRect() == null ? e1.getLine2D() : e1.getRect();
//					Object shape2 = e2.getRect() == null ? e2.getLine2D() : e2.getRect();
					
					Rectangle rect;
					Object other;
					
					boolean otherIsLine = false;
					if (e1.getRect() != null) {
						rect = e1.getRect();
						if (e2.getRect() != null) {
							otherIsLine = false;
							other = e2.getRect();
						} else if (e2.getLine() != null) {
							otherIsLine = true;
							other = e2.getLine();
						} else {
							continue;
						}
					} else if (e2.getRect() != null) {
						rect = e2.getRect();
						if (e1.getRect() != null) {
							otherIsLine = false;
							other = e1.getRect();
						} else if (e1.getLine() != null) {
							otherIsLine = true;
							other = e1.getLine();
						} else {
							continue;
						}
					} else {
						continue;
					}
					
//					Object rect = e1.getRect() == null ? e2 : e1;
//					if (!(rect instanceof Rectangle))
//						continue;
//					Object other = rect == e1 ? e2 : e1;
//					
//					boolean line = ((RootEntity) other).getLine() != null;
//					
					if (!otherIsLine) {
						if (((Rectangle) rect).intersects((Rectangle) other)) {
							e1.collided(e2);
							e2.collided(e1);
						}
					} else {
						Line l = (Line) other;
						if (((Rectangle) rect).intersectsLine(l.x1, l.y1, l.x2, l.y2)) {
							e1.collided(e2);
							e2.collided(e1);
						}
					}
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		for (RootEntity e1 : getEntities()) 
			e1.mousePressed(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		for (RootEntity e1 : getEntities()) 
			e1.mouseMoved(e);
	}

	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public abstract void keyTyped(KeyEvent e);
	
	/** Override */
//	public void keyTyped(KeyEvent e) {
//		System.out.println("Screen KeyCode: " + e.getKeyChar());
//	}
	
}
