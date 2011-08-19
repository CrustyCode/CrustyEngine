package org.crusty.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

import org.crusty.engine.entity.RootEntity;

public abstract class Screen {

	private DepthComparator depthComparator = new DepthComparator();
	
	/** Entities in Screen */
	protected PriorityBlockingQueue<RootEntity> entities;
	
	private CrustyEngine engine;
	
	public Screen(CrustyEngine engine) {
		// Load Entities
		entities = new PriorityBlockingQueue<RootEntity>(10, depthComparator);
		this.engine = engine;
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
	}

	public void mousePressed(MouseEvent e) {
		for (RootEntity e1 : getEntities()) 
			e1.mousePressed(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		for (RootEntity e1 : getEntities()) 
			e1.mouseMoved(e);
	}

	public abstract void keyTyped(KeyEvent e);
	
	/** Override */
//	public void keyTyped(KeyEvent e) {
//		System.out.println("Screen KeyCode: " + e.getKeyChar());
//	}
	
}
