package Order.TestHelpers;

import java.util.*;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 1/12/14
 */
public class MapToTable {

	public static String mapToTable(Map<?, ?> hash) {
		StringBuilder sb = new StringBuilder();
		for (Object key : hash.keySet()) {
			sb.append("\t|" +  key.toString() + "|");
		}
		sb.append("\n");
		for (Object key : hash.keySet()) {
			Object value = hash.get(key);
			sb.append("\t|" + value.toString() + "|");
		}
		sb.append("\n");

		return sb.toString();
	}
}