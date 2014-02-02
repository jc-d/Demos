package Reflections.Generators;

import Reflections.*;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 2/1/14
 */
public class StateGenerator extends GenericGenerator {
	@Override
	public List<DynamicData> generateFields() {
		List<DynamicData> data = new ArrayList<DynamicData>();
		data.add(new DynamicData("ID", "Idaho, valid state", DynamicDataMetaData.PositiveTest));
		data.add(new DynamicData("FL", "Florida, valid state", DynamicDataMetaData.PositiveTest));
		data.add(new DynamicData("Switzerland", "coutry, not a valid state", DynamicDataMetaData.NegativeTest));

		return data;
	}
}