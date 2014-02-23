package Parametrized;

import Order.TestHelpers.LogMe;
import TestNG.AnnotationTransformer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 2/22/14
 */
public class ParametrizedAnnotationTransformer {
	@DataProvider()
	public static Object[][] myData() {
		Object[][] testValues = new Object[4][1];
		testValues[0][0] = "Class: ParametrizedAnnotationTransformer";
		testValues[1][0] = "A";
		testValues[2][0] = "B";
		testValues[3][0] = "C";

		return testValues;
	}

	//How does this work?
	//1. If I right-click run TestNG gets a class/test filter and looks for a @Test, otherwise it searches for all @Tests.
	//2. With the @Test, it looks at the attributes
	@Test(dataProviderClass = ParametrizedAnnotationTransformer.class, dataProvider = "myData")
	public void aTest(String message) {
		LogMe.log(message);
	}
}