package org.crusty.engine;

import java.util.ArrayList;

public class Timeout {
	
	private static ArrayList<Long> lastTime = new ArrayList<Long>();
	private static ArrayList<Long> timeOut = new ArrayList<Long>();

	private static int num = 0;
	
	/** Returns a free index to use for a timeout */
	public static int getFreeIndex() {
		return num++;
	}
	
	/** Sets the timeout time for that specific index */
	public static void setTimeout(int index, long timeout) {
		if (index > timeOut.size() - 1) { 
			timeOut.add(index, new Long(timeout));
			lastTime.add(index, new Long(0));
		} else
			timeOut.set(index, timeout);
	}
	
	/** Resets the timeout */
	public static void reset(int index) {
		lastTime.set(index, System.currentTimeMillis());
	}

	/** Has the timeout time passed? */
	public static boolean ready(int i) {
		return lastTime.get(i) + timeOut.get(i) < System.currentTimeMillis();
	}
	
}
