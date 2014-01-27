package Order.Data;

import Order.Data.Address;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class ShipRequest {
	private Address shippingAddress;
	private Address billingAddress;
	private boolean approved;

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}
