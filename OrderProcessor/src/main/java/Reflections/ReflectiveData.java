package Reflections;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/12/14
 */
public class ReflectiveData<T> {
	private T object;
	private Map<String, DynamicData> fieldNameToDynamicData;

	public ReflectiveData(T o) {
		object = o;
		fieldNameToDynamicData = new HashMap<String, DynamicData>();
	}

	public T getObject() {
		return object;
	}

	public Map<String, DynamicData> getFieldNameToDynamicData() {
		return fieldNameToDynamicData;
	}
}
