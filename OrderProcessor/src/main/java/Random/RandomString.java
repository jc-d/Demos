package Random;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/22/13
 */
public class RandomString {

	public static String randomAlphaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public static String randomUnicode(int length) {
		return RandomStringUtils.random(length);
	}

	public static String randomAddress1() {
		return RandomNumber.randomInt(4) + " " + RandomStringUtils.random(1, new char[]{'N', 'S', 'E', 'W'}) +
				" " + randomAlphaNumeric(14);
	}
}
