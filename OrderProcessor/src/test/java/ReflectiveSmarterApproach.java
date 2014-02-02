import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.*;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class ReflectiveSmarterApproach extends GenericTest {
	@Test()
	public void reflectSmart() {
		List<DynamicDataMetaData> exclude = new ArrayList<DynamicDataMetaData>();
		//exclude.add(DynamicDataMetaData.NegativeTest);
		exclude.add(DynamicDataMetaData.EmptyValue);
		testWithReflections(exclude);
	}

	public void testWithReflections(List<DynamicDataMetaData> exclude) {
		Order o = new Order();
		ReflectiveData<Address> shippingAddress = new CreateInstanceOfData<Address>().setObject(new Address(), exclude);
		o.setShippingAddress(shippingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(shippingAddress.getFieldNameToDynamicData()));

		ReflectiveData<Address> billingAddress = new CreateInstanceOfData<Address>().setObject(new Address(), exclude);
		o.setBillingAddress(billingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(billingAddress.getFieldNameToDynamicData()));

		DynamicFieldData d = new DynamicFieldData(14);
		d.setFieldsDynamically(o.getCard());

		Item item = new Item();
		item.setName("Test Pro 3000!");
		item.setDescription("1000x better than Test Pro 3!");
		item.setCost(300.30);
		item.setAbleToOrder(true);
		item.setManufacturer("JCD Inc.");
		item.setVendorCode("ABC-123");
		o.getItems().add(item);

		printOrder(o);
		Throwable error=null;
		//Finally to the test!
		OrderProcessing processing = new OrderProcessing();
		ShipRequest request = null;
		try {
			request = processing.processOrder(o);
		} catch (Throwable t) {
			error = t;
		}
		boolean atLeastOneErrorFound = false;
		int atLeastOneErrorIsRightClass = -1;//TODO trillean.
		Map<String, Boolean> hasNameInErrors = new HashMap<String, Boolean>();
		for(Map.Entry<String, DynamicData> entry : shippingAddress.getFieldNameToDynamicData().entrySet()) {
			if(entry.getValue().getErrorClass()!= null && error!=null && atLeastOneErrorIsRightClass!=1) {
				atLeastOneErrorIsRightClass=0;
				if(entry.getValue().getErrorClass().equals(error.getClass())) {
					atLeastOneErrorIsRightClass=1;
				}
			}
			if(entry.getValue().getMetaData().contains(DynamicDataMetaData.NegativeTest)) {
				atLeastOneErrorFound = true;
				if(error==null)
					throw new Error("Expected error in " + entry.getKey() + " but didn't get one.");
				if(processing!=null) {
					hasNameInErrors.put(entry.getKey(), false);
					for(String errorMessage : processing.getErrors()) {
						if(errorMessage.contains(entry.getKey())) {
							hasNameInErrors.put(entry.getKey(), true);
							break;
						}
					}
				}
			}
		}
		if(error!=null && !atLeastOneErrorFound)
			throw new Error("An exception was thrown, but none was expected.", error);

		//Verify
		if(request!=null)
			ShipRequestVerifier.verify(request, o);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, Boolean> entry : hasNameInErrors.entrySet()) {
			if(!entry.getValue()) {
				sb.append("Name " + entry.getKey() + " was not found in the errors but was expected.\n");
			}
		}
		if(sb.length()!=0) {
			LogMe.log("ERRORS: " + sb.toString());
			throw new Error(sb.toString());
		}
	}
}