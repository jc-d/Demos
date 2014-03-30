package Order;

import Order.Data.*;
import Reflections.Fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class OrderProcessing {

	private List<String> errors = new ArrayList<String>();

	public List<String> getErrors() { return errors; }

	private boolean isOrderValid(Order o) {
		if(o.getItems().size()>10) return false;
		if(!validateState(o.getBillingAddress().getState())) errors.add("state: Invalid Billing State");
		if(!validateState(o.getShippingAddress().getState())) errors.add("state: Invalid Shipping State");
		if(o.getCard().getCardNumber().length()>20 || o.getCard().getCardNumber().length()<2) errors.add("cardNumber: Invalid Credit Card #");

        if(o.getVersion()>3) {
            addressValidator(o.getBillingAddress(), "Billing");//TODO FIX ME
            addressValidator(o.getShippingAddress(), "Shipping");
        }

		for(Item i : o.getItems()) {
            if(i.isAbleToOrder()) {
                if(i.getCost()>0 || (i.getCost()>=0 && o.getVersion()>2)) {//TODO FIX ME
                    if(Utils.hasUnicode(i.getName())) {
                        errors.add("item name: " + i.getName() + " contains non-ascii characters.");
                    }
                } else {
                    errors.add("cost: Below 0 [" + i.getCost() + "]");//intentional error, includes 0
                }
            }
        }
        if(errors.size()==0)
			return true;
		return false;
	}

    public void addressValidator(Address address, String type) {
        for(Map.Entry<String, Object> field : Fields.getFields(address).entrySet()) {
            if(field.getValue() == null) continue;//not sure what to do here...
            if(Utils.hasUnicode(field.getValue() + "")) {
                errors.add("Address " + type + " has unicode characters in " + field.getKey());
            }
            if("".equals(field.getValue())) {
                errors.add("Address " + type + " is blank in " + field.getKey());
            }
            if(field.getValue().toString().length()>40) {
                errors.add("Address " + type + " is too long in " + field.getKey());
            }
        }
    }

	private boolean validateState(String state) {
		if(state.equalsIgnoreCase("ID")) return true;
		if(state.equalsIgnoreCase("FL")) return true;
		return false;
	}

	public ShipRequest processOrder(Order o) {
		if(!isOrderValid(o))
			throw new InvalidDataError("Unable to process order");
		ShipRequest r = new ShipRequest();
		r.setBillingAddress(o.getBillingAddress());
		if(o.getVersion()==1)
			r.setShippingAddress(o.getBillingAddress());
		else
			r.setShippingAddress(o.getShippingAddress());

		return r;
	}
}
