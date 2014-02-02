import Order.Data.*;
import Order.*;
import Order.TestHelpers.AddressHelper;
import Order.TestHelpers.ShipRequestVerifier;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class HardcodedNaiveApproach extends GenericTest {
	@Test()
	public void order1() {
		Order o = new Order();
		o.getBillingAddress().setAddress1("123 2nd 1/2 street");
		o.getBillingAddress().setAddress2("Unit 11");
		o.getBillingAddress().setCity("Meridian");
		o.getBillingAddress().setName("JCD");
		o.getBillingAddress().setState("ID");

		o.setShippingAddress(AddressHelper.createAddressCopy(o.getBillingAddress()));

		CreditCard c = new CreditCard();
		c.setName("JCD");
		c.setCardNumber("1234-5678-9102-3456");
		c.setCcv(100);

		o.setCard(c);

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