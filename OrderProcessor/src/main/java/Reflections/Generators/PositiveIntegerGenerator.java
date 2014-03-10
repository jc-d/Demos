package Reflections.Generators;

import Order.Data.InvalidDataError;
import Random.RandomNumber;
import Random.RandomString;
import Reflections.DynamicData;
import Reflections.DynamicDataMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class PositiveIntegerGenerator extends GenericGenerator {

	@Override
	public List<DynamicData> generateFields() {

		List<DynamicData> fields = new ArrayList<DynamicData>();
		long value = 0;
		fields.add(new DynamicData(value, "Zero",
				new DynamicDataMetaData[] {DynamicDataMetaData.PositiveTest, DynamicDataMetaData.ZeroValue}));

        fields.add(new DynamicData(Integer.MAX_VALUE, "Int.Max",
                new DynamicDataMetaData[] {DynamicDataMetaData.PositiveTest, DynamicDataMetaData.Boundary}));

        fields.add(new DynamicData(Integer.MIN_VALUE, "Int.Min",
                new DynamicDataMetaData[] {DynamicDataMetaData.NegativeTest, DynamicDataMetaData.Boundary}).
                setErrorClass(InvalidDataError.class));

		return fields;
	}
}