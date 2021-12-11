package com.shawtonabbey.pgem.plugin.csv.models;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({ FIELD, METHOD })
public @interface Column {

	int idx();
	String name();
	
}

