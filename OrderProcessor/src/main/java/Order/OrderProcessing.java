package Order;

import Order.Data.InvalidDataError;
import Order.Data.Order;
import Order.Data.ShipRequest;

import java.util.ArrayList;
import java.util.List;

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
		if(errors.size()==0)
			return true;
		return false;
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
