package org.crusty.engine;

import java.util.concurrent.PriorityBlockingQueue;

import org.crusty.engine.entity.RootEntity;

public class EventSystem {

	PriorityBlockingQueue<RootEntity> registeredObjects;
	
	public EventSystem() {
		registeredObjects = new PriorityBlockingQueue<RootEntity>();
	}
	
	public void registerObject(RootEntity o) {
		if (!registeredObjects.contains(o))
			registeredObjects.add(o);
		else {
			System.err.println("Already contains object: " + o);
			System.exit(-1);
		}
	}
	
	public void deregisterObject(RootEntity o) {
		registeredObjects.remove(o);
	}
	
	public void sendMessage(String s, RootEntity entity) {
		for (RootEntity e : registeredObjects) {
			e.message(s, entity);
		}
	}
	
}
