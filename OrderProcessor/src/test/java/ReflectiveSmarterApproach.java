import Order.Data.*;
import Order.*;
import Order.TestHelpers.*;
import Reflections.CreateInstanceOfData;
import Reflections.DynamicFieldData;
import Reflections.ReflectiveData;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class ReflectiveSmarterApproach {
	@Test()
	public void reflectSmart() {
		Order o = new Order();
		ReflectiveData<Address> shippingAddress = new CreateInstanceOfData<Address>().setObject(new Address());
		o.setShippingAddress(shippingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(shippingAddress.getFieldNameToDynamicData()));

		ReflectiveData<Address> billingAddress = new CreateInstanceOfData<Address>().setObject(new Address());
		o.setBillingAddress(billingAddress.getObject());

		LogMe.log(MapToTable.mapToTable(billingAddress.getFieldNameToDynamicData()));

		DynamicFieldData d = new DynamicFieldData(30);
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
