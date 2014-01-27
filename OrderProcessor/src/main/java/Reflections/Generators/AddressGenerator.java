package Reflections.Generators;

import Order.Data.InvalidDataError;
import Reflections.*;
import Random.*;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class AddressGenerator extends GenericGenerator {
	@Override
	public List<DynamicData> generateFields() {
		List<DynamicData> fields = new ArrayList<DynamicData>();
		fields.add(new DynamicData(RandomString.randomAddress1(), "Address", DynamicDataMetaData.PositiveTest));
		fields.add(new DynamicData("", "Empty",
				new DynamicDataMetaData[] {DynamicDataMetaData.NegativeTest, DynamicDataMetaData.EmptyValue}).
				setException(InvalidDataError.class));

		return fields;
	}
}