import Order.Data.Order;
import Order.TestHelpers.LogMe;
import Reflections.Fields;

import java.util.Collection;
import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 2/1/14
 */
public class GenericTest {

	public void printOrder(Order o) {
		if(o==null) return;
		for(String s : Fields.getFieldNames(o)) {
			LogMe.log(getPrintableDataFromObject(o, s));
		}
	}

	public String getPrintableDataFromObject(Object o, String fieldName) {
		return getPrintableDataFromObject(o, fieldName, "");//tab level 0
	}

	private String getPrintableDataFromObject(Object o, String fieldName, String tabs) {
		Object obj = Fields.getFieldValue(fieldName, o);
		String value = obj + "";
		if(value.contains("@") && value.contains("Order.Data")) {//TODO regex me
			StringBuilder sb = new StringBuilder();
			sb.append(tabs + fieldName + ":\n");
			if(obj instanceof Collection<?>) {
				//unroll the collection.
				Collection<?> col = (Collection<?>) obj;
				for(Object item : col) {
					sb.append(tabs + "\tCollection Item:\n");
					for(String subField : Fields.getFieldNames(item)) {
						sb.append(tabs + "\t\t" + getPrintableDataFromObject(item, subField, tabs + "\t") + "\n");
					}
				}
			} else {
				for(String subField : Fields.getFieldNames(obj)) {
					sb.append(tabs + "\t" + getPrintableDataFromObject(obj, subField, tabs + "\t") + "\n");
				}
			}
			return sb.toString();
		}
		else
			return fieldName +  " = " + value;
	}
}
