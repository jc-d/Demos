package Reflective;

import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.*;
import Helpers.GenericTest;
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

        ValidationData validationData = verifyData(shippingAddress, "ShippingAddress", error, processing);

        if(error!=null && !validationData.atLeastOneErrorFound)
			throw new Error("An exception was thrown, but none was expected.", error);

		//Verify
		if(request!=null)
			ShipRequestVerifier.verify(request, o);

		if(validationData.getErrors().length()!=0) {
			LogMe.log("ERRORS: \n\n" + validationData.getErrors());
			throw new Error("\n" + validationData.getErrors());
		}
	}

    private class ValidationData {
        private StringBuilder errorMessages = new StringBuilder();
        public boolean atLeastOneErrorFound;
        public Map<String, Boolean> hasNameInErrors = new HashMap<String, Boolean>();
        public void addError(String error) {
            if(error == null || error.length()==0) return;
            errorMessages.append(" - Error: " + error + "\n");
        }
        public void addData(ValidationData data) {
            this.addError(data.getErrors());
            if(data.atLeastOneErrorFound) this.atLeastOneErrorFound = true;
        }
        public String getErrors() { return errorMessages.toString(); }
    }

    private ValidationData verifyData(ReflectiveData<?> data, String type, Throwable error, OrderProcessing processing) {

        ValidationData validator = new ValidationData();
        validator.atLeastOneErrorFound = false;
        int atLeastOneErrorIsRightClass = -1;//TODO trillean.
        for(Map.Entry<String, DynamicData> entry : data.getFieldNameToDynamicData().entrySet()) {
            if(entry.getValue().getErrorClass()!= null && error!=null && atLeastOneErrorIsRightClass!=1) {
                atLeastOneErrorIsRightClass=0;
                if(entry.getValue().getErrorClass().equals(error.getClass())) {
                    atLeastOneErrorIsRightClass=1;
                }
            }
            if(entry.getValue().getMetaData().contains(DynamicDataMetaData.NegativeTest)) {
                validator.atLeastOneErrorFound = true;
                if(error==null) {
                    validator.addError("Expected error in <" + entry.getKey() + "> of " + type + " but didn't get one.");
                }
                if(processing!=null) {
                    validator.hasNameInErrors.put(entry.getKey(), false);
                    for(String errorMessage : processing.getErrors()) {
                        if(errorMessage.contains(entry.getKey())) {
                            validator.hasNameInErrors.put(entry.getKey(), true);
                            break;
                        }
                    }
                }
            }
        }
        for(Map.Entry<String, Boolean> entry :  validator.hasNameInErrors.entrySet()) {
            if(!entry.getValue()) {
                DynamicData dynamicData = data.getFieldNameToDynamicData().get(entry.getKey());
                validator.addError("Name <" + entry.getKey() + "> was not found in the errors for " + type + " but was expected. " +
                        "Debug Data:\nComment: " + dynamicData.getComment() + "; Value: <" + dynamicData.toString() + ">.");
            }
        }

        return validator;
    }
}