package com.shawtonabbey.pgem.database.deserializers;

import java.lang.reflect.Constructor;
import com.shawtonabbey.pgem.database.ARecordSet;

public class Constr<T> implements Deserializer<T> {

	private Constructor<?> construct;
	
	public Constr(Class<T> cls) {
		
		construct = cls.getConstructors()[0];
	}
	
	@Override
	public T write(ARecordSet set) throws Exception {
		
		var cols = set.getColumnCount();
		
		var cArgs = new Object[cols];
		
		for (var i = 1; i <= cols; i++) {						
			cArgs[i-1] = set.getValue(i);
		}
		@SuppressWarnings("unchecked")
		var c = (T)construct.newInstance(cArgs);
		
		return c;
	}

}
