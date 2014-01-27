package Random;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/22/13
 */
public class RandomNumber {

	private static final Random random = new Random();

	public static int between(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	public static String randomInt(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public static int randomInt() {
		return random.nextInt();
	}

	public static double randomDouble() {
		return random.nextDouble();
	}

	public static float randomFloat() {
		return random.nextFloat();
	}
}