package Reflections;

import Random.RandomNumber;
import Random.RandomString;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/22/13
 */
public class DynamicFieldData {

	private int stringLength = 30;

	public DynamicFieldData(int stringLength) {
		this.stringLength = stringLength;
	}

	public  void setFieldsDynamically(Object object) {
		List<String> list = Fields.getFieldNames(object);
		for(String fieldName : list) {
			Field f = Fields.getField(object, fieldName);
			setValue(f, object);
		}
	}

	protected void setValue(Field f, Object object) {
		try {
			Class<?> t = f.getType();
			if(String.class.equals(t)) {
				f.set(object, RandomString.randomAlphaNumeric(stringLength));
			} else if(int.class.equals(t) || Integer.class.equals(t)) {
				f.set(object, RandomNumber.randomInt());
			} else if(float.class.equals(t) || Float.class.equals(t)) {
				f.set(object, RandomNumber.randomFloat());
			} else if(double.class.equals(t) || Double.class.equals(t)) {
				f.set(object, RandomNumber.randomDouble());
			}
		}catch (Throwable t) { throw new Error(t); }
	}
}
