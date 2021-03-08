package net.natroutter.natlibs.utilities.serialize;

import java.lang.reflect.Field;

public class GsonFieldExeption extends Exception {

	private static final long serialVersionUID = -135785561545832646L;

	private final Field field;
	
	public GsonFieldExeption(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}
	
	@Override
	public String getMessage() {
		return "Missing field in JSON: " + field.getName();
	}
}
