package Order;

import Order.Data.InvalidDataError;
import Order.Data.Order;
import Order.Data.ShipRequest;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class OrderProcessing {

	private boolean isOrderValid(Order o) {
		if(o.getItems().size()>10) return false;
		return true;
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
