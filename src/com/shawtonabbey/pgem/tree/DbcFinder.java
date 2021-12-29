package com.shawtonabbey.pgem.tree;

import com.shawtonabbey.pgem.database.DBC;

public interface DbcFinder {

	public default DBC FindDbc() {
		
		if (this instanceof Group) {
			var t = ((Group)this);
			
			if (t.parent instanceof Group)
				return ((Group)t.parent).FindDbc();
			
			if (t.parent instanceof Instance)
				return ((Instance)t.parent).FindDbc();
		}
		
		if (this instanceof Instance) {
			var t = ((Instance)this);
			
			if (t.parent instanceof Group)
				return ((Group)t.parent).FindDbc();
			
			if (t.parent instanceof Instance)
				return ((Instance)t.parent).FindDbc();
		}
		
		return null;
		
	}
	
}
