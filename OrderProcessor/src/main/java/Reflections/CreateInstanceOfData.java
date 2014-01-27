package Reflections;

import Random.RandomNumber;
import sun.net.www.content.image.x_xpixmap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class CreateInstanceOfData<T> {

	public ReflectiveData<T> setObject(T object) {
		if(object==null) throw new Error("Unable to create null object");
		ReflectiveData<T> data = new ReflectiveData<T>(object);

		for(Field f : object.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			for(Annotation ann : f.getDeclaredAnnotations()) {
				if(ann.annotationType().equals(FieldData.class)) {
					FieldData fd =  (FieldData) ann;
					List<DynamicData> values = new ArrayList<DynamicData>();
					for(Class cl :  fd.dataGenerators()) {
						try {
							IDataGenerator generator = (IDataGenerator) cl.newInstance();
							generator.constructor();
							values.addAll(generator.generateFields());
						}catch (Throwable t) {
							throw new Error("Failed to create new instance.  Your type might not come from IDataGenerator or generation failed.", t);
						}
					}
					DynamicData value = (DynamicData) values.get(RandomNumber.between(0, values.size() - 1));
					try {
						f.set(object, value.getValue());
						data.getFieldNameToDynamicData().put(f.getName(), value);
					} catch (Throwable t) {
						throw new Error("Failed to set value to " + value + " in object given.", t);
					}
				}
			}
		}
		return data;
	}
}