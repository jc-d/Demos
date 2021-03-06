package HardCoded;

import Order.Data.*;
import Order.*;
import Order.TestHelpers.AddressHelper;
import Order.TestHelpers.LogMe;
import Order.TestHelpers.ShipRequestVerifier;
import Helpers.GenericTest;
import com.sun.deploy.util.StringUtils;
import org.testng.annotations.Test;
import Random.*;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class HardcodedSmarterApproach extends GenericTest {

	@Test()
	public void smarterOrder1() {
		Order o = new Order();
		o.getBillingAddress().setAddress1(RandomNumber.between(1, 10000) + " 2nd 1/2 street");
		o.getBillingAddress().setAddress2("Unit 11");
		o.getBillingAddress().setCity("Meridian");
		o.getBillingAddress().setName("JCD");
		o.getBillingAddress().setState("ID");

		o.setShippingAddress(AddressHelper.createAddressCopy(o.getBillingAddress()));

		CreditCard c = new CreditCard();
		c.setName("JCD");
		c.setCardNumber(RandomNumber.randomInt(4) + "-" + RandomNumber.randomInt(4) +
				"-" + RandomNumber.randomInt(4) + "-" + RandomNumber.randomInt(4));
		c.setCcv(100);

		o.setCard(c);

		List<Item> items = o.getItems();
		items.add(createItem());
		items.add(createItem());
		items.add(createItem());
		items.add(createItem());

		printOrder(o);

		//Finally to the test!
		OrderProcessing processing = new OrderProcessing();
        try {
		    ShipRequest request = processing.processOrder(o);
            //Verify
            ShipRequestVerifier.verify(request, o);
        }catch (Throwable t) {
            String error = "Error: " + t.toString() + "\n Details: " + Arrays.toString(processing.getErrors().toArray());
            LogMe.log(error);
            throw new Error(error, t);
        }
	}

	//Pretend this is behinds a framework if you like.
    //But notice how we are forced to 'hard code' unicode characters into each field.  How do we test each field?
    //How do you trust their isn't a relationship between the fields or that if all the fields were at max
    //length or ... that it wouldn't bust?
	public Item createItem() {
		Item item = new Item();
		item.setName("Test Pro 3000! - "  + RandomString.randomUnicode(10));
		item.setDescription("1000x better than Test Pro 3! - " + RandomString.randomUnicode(10));
		item.setCost(RandomNumber.randomDouble());
		item.setAbleToOrder(true);
		item.setManufacturer("JCD Inc.");
		item.setVendorCode("ABC-123");
		return item;
	}
}