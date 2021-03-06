package Reflections.Generators;

import Order.Data.InvalidDataError;
import Random.RandomNumber;
import Random.RandomString;
import Reflections.DynamicData;
import Reflections.DynamicDataMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy Carey-dressler on 3/11/14.
 */
public class AverageSizedStringPositiveGenerator extends AverageSizedStringGenerator {

    @Override
    public List<DynamicData> generateFields() {

        List<DynamicData> fields = new ArrayList<DynamicData>();
        for(DynamicData field :  super.generateFields()) {
            if(field.getMetaData().contains(DynamicDataMetaData.PositiveTest) &&
                    !field.getMetaData().contains(DynamicDataMetaData.Unicode)) {
                fields.add(field);
            }
        }

        return fields;
    }
}
