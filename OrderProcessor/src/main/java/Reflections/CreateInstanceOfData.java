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

	public ReflectiveData<T> setObject(T object, List<DynamicDataMetaData> excludeList) {
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
							List<DynamicData> valuesToFilter = generator.generateFields();
							for(DynamicData value : valuesToFilter) {
								boolean excludeValue = false;
								for(DynamicDataMetaData exclude : excludeList) {
									if(value.getMetaData().contains(exclude)) {
										excludeValue = true;
										break;
									}
								}
								if(!excludeValue)
									values.add(value);
							}

						}catch (Throwable t) {
							throw new Error("Failed to create new instance.  Your type might not come from IDataGenerator or generation failed.", t);
						}
						if(values.size()==0) {
							throw new Error("Failed to generate any values");
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


	public ReflectiveData<T> setObject(T object) {
		return setObject(object, new ArrayList<DynamicDataMetaData>());
	}
}