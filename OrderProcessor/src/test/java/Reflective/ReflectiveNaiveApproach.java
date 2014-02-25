package Reflective;

import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.DynamicFieldData;
import Helpers.GenericTest;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/22/13
 */
public class ReflectiveNaiveApproach extends GenericTest {

	@Test()
	public void reflect() {
		Order o = new Order();

		DynamicFieldData d = new DynamicFieldData(30);
		d.setFieldsDynamically(o.getBillingAddress());
		d.setFieldsDynamically(o.getShippingAddress());
		o.getBillingAddress().setState("ID");
		o.getShippingAddress().setState("ID");
		d.setFieldsDynamically(o.getCard());
		o.getCard().setCardNumber(o.getCard().getCardNumber().substring(0, 12));

		List<Item> items = o.getItems();
		Item item = new Item();
		item.setName("Test Pro 3000!");
		item.setDescription("1000x better than Test Pro 3!");
		item.setCost(300.30);
		item.setAbleToOrder(true);
		item.setManufacturer("JCD Inc.");
		item.setVendorCode("ABC-123");
		items.add(item);

		printOrder(o);

		//Finally to the test!
		OrderProcessing processing = new OrderProcessing();
		ShipRequest request = processing.processOrder(o);
		//Verify
		ShipRequestVerifier.verify(request, o);

	}
}