package Order.TestHelpers;

import Order.Data.*;
import Reflections.Fields;

import java.util.Map;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class ShipRequestVerifier {

	public static void verify(ShipRequest request, Order order) {
		verifyObjectsAreEqual(order.getShippingAddress(), request.getShippingAddress());
		verifyObjectsAreEqual(order.getBillingAddress(), request.getBillingAddress());
	}

	private static void verifyObjectsAreEqual(Object order, Object request) {
		verifyMapsAreEqual(Fields.getFields(order), Fields.getFields(request));
	}

	private static void verifyMapsAreEqual(Map<String, Object> order, Map<String, Object> request) {
		for(String key : order.keySet()) {
			if(!request.containsKey(key)) continue;

			Object expected = order.get(key);
			Object actual = request.get(key);
			if(expected==null && actual == null) continue;
			if(expected==null || !expected.equals(actual)) throw new Error("Expected: " + expected + " != " + actual);
			LogMe.log("expected: " + expected + " actual: " + actual);
		}
	}
}
