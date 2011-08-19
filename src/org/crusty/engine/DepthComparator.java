package org.crusty.engine;



import java.util.Comparator;

import org.crusty.engine.entity.RootEntity;

public class DepthComparator implements Comparator<RootEntity> {

	public int compare(RootEntity e1, RootEntity e2) {
		return e1.depth - e2.depth;
	}
	
}
