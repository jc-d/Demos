package Reflections;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class Fields {
	public static List<String> getFieldNames(Object object) {
		List<String> names = new ArrayList<String>();
		for(Field f : object.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			names.add(f.getName());
		}
		return names;
	}

	public static Object getFieldValue(String fieldName, Object object) {
		try{
			Field f = getField(object, fieldName);
			return f.get(object);
		}catch (Throwable t) {
			throw new Error(t);
		}
	}

	public static Map<String, Object> getFields(Object object) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(String item : getFieldNames(object)) {
			map.put(item, getFieldValue(item, object));
		}
		return map;
	}

	public static Field getField(Object object, String fieldName) {
		try{
			Field f = object.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f;
		}catch (Throwable t) {
			throw new Error(t);
		}
	}

	public static void setField(String fieldName, Object object, Object value) {
		try{
			Field f = getField(object, fieldName);
			f.set(object, value);
		}catch (Throwable t) {
			throw new Error(t);
		}

	}
}
