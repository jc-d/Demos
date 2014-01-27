package Order.TestHelpers;

import Order.Data.Address;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class AddressHelper {

	public static Address createAddressCopy(Address address) {
		Address a = new Address();
		a.setState(address.getState());
		a.setName(address.getName());
		a.setAddress1(address.getAddress1());
		a.setAddress2(address.getAddress2());
		a.setCity(address.getCity());
		a.setCountry(address.getCountry());

		return a;
	}
}
