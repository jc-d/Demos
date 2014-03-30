package SimpleReflections;

import Order.Data.Item;
import Order.Data.Order;
import Order.TestHelpers.LogMe;
import Reflections.FieldData;
import Reflections.Fields;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by Jeremy Carey-dressler on 3/13/14.
 */
public class DebugData {

    /**
     * This is a simple non-framework method to demonstrate how to print out debug data.
     */
    @Test()
    public void debugAnOrder() {
        Item item = new Item();
        item.setName("Test Pro 3000!");
        item.setDescription("1000x better than Test Pro 3!");
        item.setCost(300.30);
        item.setAbleToOrder(true);
        item.setManufacturer("JCD Inc.");
        item.setVendorCode("ABC-123");

        for(Map.Entry<String, Object> entry : Fields.getFields(item).entrySet()) {
            LogMe.log("Field Name: " + entry.getKey() + " ; Value: " + entry.getValue());
        }
    }

}
