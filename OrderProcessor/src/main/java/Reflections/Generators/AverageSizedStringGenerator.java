package Reflections.Generators;

import Order.Data.InvalidDataError;
import Reflections.*;

import java.util.*;
import Random.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public class AverageSizedStringGenerator extends GenericGenerator {

	protected int maxLength = 40;
	protected int minLength = 10;

	protected DynamicDataMetaData isPositiveOrNegative(String value) {
		if(value==null) return DynamicDataMetaData.NegativeTest;
		if(value.length() >= minLength) {
			if(value.length() < maxLength)
				return DynamicDataMetaData.PositiveTest;
		}
		return DynamicDataMetaData.NegativeTest;
	}

	@Override
	public List<DynamicData> generateFields() {

		List<DynamicData> fields = new ArrayList<DynamicData>();
		String value = "";
		fields.add(new DynamicData(value, "Empty",
				new DynamicDataMetaData[] {isPositiveOrNegative(value), DynamicDataMetaData.EmptyValue}).
				setErrorClass(InvalidDataError.class));
		value = RandomString.randomUnicode(RandomNumber.between(minLength, maxLength));
		fields.add(new DynamicData(value, "Unicode",
				new DynamicDataMetaData[] {isPositiveOrNegative(value), DynamicDataMetaData.Unicode}));

		value = RandomString.randomUnicode(RandomNumber.between(maxLength + 1, maxLength * 2));
		fields.add(new DynamicData(value, "Unicode",
				new DynamicDataMetaData[] {isPositiveOrNegative(value), DynamicDataMetaData.Unicode}).
				setErrorClass(InvalidDataError.class));

		value = RandomString.randomAlphaNumeric(RandomNumber.between(minLength, maxLength));
		fields.add(new DynamicData(value, "AlphaNumeric",
				new DynamicDataMetaData[] {isPositiveOrNegative(value)}));

		value = RandomString.randomAlphaNumeric(RandomNumber.between(maxLength + 1, maxLength * 2));
		fields.add(new DynamicData(value, "AlphaNumeric",
				new DynamicDataMetaData[] {isPositiveOrNegative(value)}).
				setErrorClass(InvalidDataError.class));

		return fields;
	}
}