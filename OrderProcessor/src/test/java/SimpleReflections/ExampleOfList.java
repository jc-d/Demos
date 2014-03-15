package SimpleReflections;

import Order.TestHelpers.LogMe;
import Reflections.Methods;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler on 3/13/14.
 */
public class ExampleOfList {

    @Test()
    public void runOnEachItemInList() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("TWO");
        list.add("333");
        list.add("FouR");
        for(String item : actOnString(list, "toUpperCase")) {
            LogMe.log(item);
        }
    }

    public List<String> actOnString(List<String> items, String command) {
        List<String> returnValue = new ArrayList<String>();
        for(String item : items) {
             returnValue.add(Methods.runMethodWithNoArgs(item, command).toString());
        }
        return returnValue;
    }
}
