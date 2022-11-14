package com.shawtonabbey.pgem.database.deserializers;

import com.shawtonabbey.pgem.database.ARecordSet;

public class Property<T>  implements Deserializer<T> {

	private Class<T> cls;
	
	public Property(Class<T> cls) {
		this.cls = cls;
	}
	
	
	@Override
	public T write(ARecordSet set) throws Exception {
		
		var cols = set.getColumnCount();
		
		@SuppressWarnings("deprecation")
		var c = cls.newInstance();
		
		for (var i = 1; i <= cols; i++) {
			var colName = set.getColumnName(i);
			var className = set.columnInfo.getColumnClassName(i);
			var value = set.getValue(i);
			try {
				var method = cls.getMethod("set" + fix(colName), Class.forName(className));
				method.invoke(c, value);
			} catch (NoSuchMethodException e) {}
		}
		
		return c;
	}
	
	private String fix(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
