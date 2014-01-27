package Reflections;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class DynamicData {
	private String comment;
	private Object value;
	private List<DynamicDataMetaData> data;
	private Class error;

	public DynamicData(Object value, String comment, DynamicDataMetaData data) {
		this(value, comment);
		this.data.add(data);
	}

	public DynamicData(Object value, String comment, DynamicDataMetaData[] data) {
		this(value, comment);
		for(DynamicDataMetaData datum : data)
			this.data.add(datum);
	}

	public DynamicData(Object value, String comment) {
		this.value = value;
		this.comment = comment;
		this.data = new ArrayList<DynamicDataMetaData>();
	}

	public DynamicData setException(Class t) {
		error = t;
		return this;
	}

	public List<DynamicDataMetaData> getMetaData() {
		return data;
	}

	public String getComment() {
		return comment;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		String s = "";
		for(DynamicDataMetaData datum : data)
			s = s + datum + ",";
		return getValue() + "(" + getComment() + "; " + s + ")";
	}
}