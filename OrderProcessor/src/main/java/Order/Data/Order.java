package Order.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class Order {
	private Address shippingAddress;
	private Address billingAddress;
	private List<Item> items;
	private CreditCard card;
	private int orderVersion = 2;

	public int getVersion() { return orderVersion; }

	public CreditCard getCard() {
		return card;
	}

	public void setCard(CreditCard card) {
		this.card = card;
	}

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Order() {
		shippingAddress = new Address();
		billingAddress = new Address();
		items = new ArrayList<Item>();
		card = new CreditCard();
	}
}