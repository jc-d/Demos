import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.DynamicFieldData;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/22/13
 */
public class ReflectiveNaiveApproach {

	@Test()
	public void reflect() {
		Order o = new Order();

		DynamicFieldData d = new DynamicFieldData(30);
		d.setFieldsDynamically(o.getBillingAddress());
		d.setFieldsDynamically(o.getShippingAddress());
		d.setFieldsDynamically(o.getCard());

		List<Item> items = o.getItems();
		Item item = new Item();
		item.setName("Test Pro 3000!");
		item.setDescription("1000x better than Test Pro 3!");
		item.setCost(300.30);
		item.setAbleToOrder(true);
		item.setManufacturer("JCD Inc.");
		item.setVendorCode("ABC-123");
		items.add(item);

		//Finally to the test!
		OrderProcessing processing = new OrderProcessing();
		ShipRequest request = processing.processOrder(o);
		//Verify
		ShipRequestVerifier.verify(request, o);

	}
}