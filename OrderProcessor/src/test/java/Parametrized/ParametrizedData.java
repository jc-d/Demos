package Parametrized;

import Order.TestHelpers.LogMe;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 2/22/14
 */
public class ParametrizedData {

	@DataProvider()
	public static Object[][] myData() {
		Object[][] testValues = new Object[4][1];
		testValues[0][0] = "Class: ParametrizedData";
		testValues[1][0] = "1";
		testValues[2][0] = "2";
		testValues[3][0] = "3";

		return testValues;
	}


    //How does this work?
    //1. If I right-click run TestNG gets a class/test filter and looks for a @Test, otherwise it searches for all @Tests.
    //2. With the @Test, it looks at the attributes, finds the data provider.
    //3. Looks in that class for that method.  Checks that it is static then calls it.
    //4. Takes the data and iterates over it, passing it in to "aTest"
	@Test(dataProviderClass = ParametrizedData.class, dataProvider = "myData")
	public void aTest(String message) {
		LogMe.log(message);
	}
}