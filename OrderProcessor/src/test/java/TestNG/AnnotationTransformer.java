package TestNG;

import Parametrized.ParametrizedData;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 2/22/14
 */
public class AnnotationTransformer implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
		iTestAnnotation.setDataProviderClass(ParametrizedData.class);
	}
}
