package Reflections;

import java.util.List;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/11/14
 */
public interface IDataGenerator {
	public List<DynamicData> generateFields();
	public void constructor();
}
