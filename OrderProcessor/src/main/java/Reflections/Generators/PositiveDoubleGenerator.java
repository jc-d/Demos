package Reflections.Generators;

import Order.Data.InvalidDataError;
import Random.RandomNumber;
import Reflections.DynamicData;
import Reflections.DynamicDataMetaData;

import java.util.List;

/**
 * Created by Jeremy Carey-dressler on 3/9/14.
 */
public class PositiveDoubleGenerator extends PositiveIntegerGenerator {

    @Override
    public List<DynamicData> generateFields() {

        List<DynamicData> fields = super.generateFields();
        double value = RandomNumber.randomDouble();
        if(value<0) value = value * -1;
        if(value==0) value = 1;
        fields.add(new DynamicData(value, "Positive Double",
                new DynamicDataMetaData[] {DynamicDataMetaData.PositiveTest}));

        value = RandomNumber.randomDouble();
        if(value>0) value = value * -1;
        if(value==0) value = -1;
        fields.add(new DynamicData(value, "Negative Double",
                new DynamicDataMetaData[] {DynamicDataMetaData.NegativeTest}).
                setErrorClass(InvalidDataError.class));

        return fields;
    }

}
