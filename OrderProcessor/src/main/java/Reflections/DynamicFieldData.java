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

    /**
     * All we are doing here is taking the field and setting it with a pre-determined
     * random value, using the typing system to do the dirty work...  we could use
     * something slightly more intelligent, like typing with name overrides, but
     * that requires lots of custom code and maintenance.  Not ideal.
     * @param field
     * @param object
     */
	protected void setValue(Field field, Object object) {
		try {
			Class<?> t = field.getType();
			if(String.class.equals(t)) {
                //I could have if random ==1 do x, random == 2 do y (say alpha, numeric, unicode, etc)
                // but how do I know what to expect?  Should it pass or fail?
				field.set(object, RandomString.randomAlphaNumeric(stringLength));
			} else if(int.class.equals(t) || Integer.class.equals(t)) {
				field.set(object, RandomNumber.randomInt());
			} else if(float.class.equals(t) || Float.class.equals(t)) {
				field.set(object, RandomNumber.randomFloat());
			} else if(double.class.equals(t) || Double.class.equals(t)) {
				field.set(object, RandomNumber.randomDouble());
			}
		}catch (Throwable t) { throw new Error(t); }
	}
}
