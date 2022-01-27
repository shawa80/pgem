package com.shawtonabbey.pgem.database.deserializers;

import com.shawtonabbey.pgem.database.ARecordSet;

public interface Deserializer<T> {

	public T write(ARecordSet set) throws Exception;
}
