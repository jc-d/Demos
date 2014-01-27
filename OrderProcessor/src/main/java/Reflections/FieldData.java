package Reflections;

import java.lang.annotation.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldData {
	public Class[] dataGenerators();
	public String comment() default "";
	//We could have versions or other filter meta data for deciding when to use a given class, etc.
}