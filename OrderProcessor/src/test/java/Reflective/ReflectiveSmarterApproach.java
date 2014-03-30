package Reflective;

import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.*;
import Helpers.GenericTest;
import org.apache.commons.lang.StringUtils;
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
		exclude.add(DynamicDataMetaData.NegativeTest);
		exclude.add(DynamicDataMetaData.EmptyValue);
		testWithReflections(exclude);
	}

    @Test()//'Prove' the system seems reasonably stable by running lots of iterations
    public void reflectSmartRunUntilDeadOr1000Runs() {
        List<DynamicDataMetaData> exclude = new ArrayList<DynamicDataMetaData>();
        exclude.add(DynamicDataMetaData.NegativeTest);
        exclude.add(DynamicDataMetaData.EmptyValue);
        for(int i = 0; i!=1000; i++) {
            LogMe.log("On Run " + i);
            LogMe.log("---------------------------------------------------");
            testWithReflections(exclude);
        }
    }

	public void testWithReflections(List<DynamicDataMetaData> exclude) {
		Order o = new Order();
        //TODO Change me to see different behaviour by version.
        Fields.setField("orderVersion", o, 2);//3+ == Fixed cost [Positive cases], 4+ == fixes address validation [Negative cases, empty]
       //o.setVersion(2);  This does not exist, only through reflections can you access this.
		ReflectiveData<Address> shippingAddress = new CreateInstanceOfData<Address>().setObject(new Address(), exclude);
		o.setShippingAddress(shippingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(shippingAddress.getFieldNameToDynamicData()));

		ReflectiveData<Address> billingAddress = new CreateInstanceOfData<Address>().setObject(new Address(), exclude);
		o.setBillingAddress(billingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(billingAddress.getFieldNameToDynamicData()));

		DynamicFieldData d = new DynamicFieldData(14);
		d.setFieldsDynamically(o.getCard());



//Walk through what this does and doesn't do.
//        ReflectiveData<Item> item = new CreateInstanceOfData<Item>().setObject(new Item(), exclude);
//        item.getObject().setAbleToOrder(true);
//        o.getItems().add(item.getObject());
//
//        LogMe.log(MapToTable.mapToTable(item.getFieldNameToDynamicData()));
//        //TODO UNCOMMENT VALIDATION BELOW!

////Comment out this code to do walk through above.
		Item item = new Item();
		item.setName("Test Pro 3000!");
		item.setDescription("1000x better than Test Pro 3!");
		item.setCost(300.30);
		item.setAbleToOrder(true);
		item.setManufacturer("JCD Inc.");
		item.setVendorCode("ABC-123");
		o.getItems().add(item);
//        //End of comment out piece.

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

        String errors ="";

        ValidationData validationData = verifyData(shippingAddress, "ShippingAddress", error, processing);
        validationData.addData(verifyData(billingAddress, "BillingAddress", error, processing));

        //Excluded if we don't generate the item using dynamic data.
        //validationData.addData(verifyData(item, "Item", error, processing));

        if(error!=null && !validationData.atLeastOneErrorFound)
            throw new Error("An exception was thrown, but none was expected (no negative values given). " +  Arrays.toString(processing.getErrors().toArray()), error);

	    //Verify
		if(request!=null)
			ShipRequestVerifier.verify(request, o);

        LogMe.log("Order Processor Errors: " +  Arrays.toString(processing.getErrors().toArray()));

        errors = validationData.getErrors();
        if(errors.length()!=0) {
			LogMe.log("ERRORS: \n\n" + errors);
			throw new Error("\n" + errors);
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