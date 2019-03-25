package drd2.rig;

import java.lang.IllegalArgumentException;
import java.util.Random;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class RandomGenerator {
    public static Random RANDOM = new Random();


    /**
     * This is the default version of {@link RandomGenerator#getRandomRarity(short)}.
     * The optional maxRarity parameter is set to 100.
     * @see RandomGenerator#getRandomRarity(short).
     */
    public static double getRandomRarity() {
        return getRandomRarity((short)100);
    }

    /**
     * This method gives you random rarity from 0 to your specified value (maxRarity) from a static context.
     * Values are distributed uniformly.
     * @param maxRarity integer value representing maximum possible rarity output.
     * @return random double value between 0 and maxValue
     */
    public static double getRandomRarity(short maxRarity) {
       return getRandomRarity(maxRarity, (short)0);
    }

    public static double getRandomRarity(short maxRarity, short minRarity) throws IllegalArgumentException {
        int distance = maxRarity - minRarity;
        return minRarity + (RANDOM.nextDouble() * distance);
    }

    /**
     * This is the default value of {@link RandomGenerator#getRandomQuality()byte, byte}
     * averageQuality is set to 0.
     * distanceBetweenRatesParameter is set to 2.
     * @return Integer value between 0 and 3.
     * @see RandomGenerator#getRandomQuality(byte, byte)
     */

    public static byte getRandomQuality() {
        return getRandomQuality((byte) 0, (byte)2);
    }

    /**
     * This is one of {@link RandomGenerator#getRandomQuality(byte, byte)} versions.
     * distanceBetweenRatesParameter value is set to 2 here.
     * @param averageQuality decides which quality is the most frequent.
     * @return Integer value between 0 and 3.
     * @see RandomGenerator#getRandomQuality(byte, byte)
     */

    public static byte getRandomQuality(byte averageQuality) {
        return getRandomQuality(averageQuality, (byte)2);
    }

    /**
     * This method gives you random whole number between 0 and 3. The value of averageQuality is generated most frequently.
     * The more far the number is from averageQuality, the less frequently it is generated.
     * @param averageQuality decides which quality is the most frequent.
     * @param distanceBetweenRatesParameter The higher this parameter is, the less frequently are numbers other than averageQuality generated.
     * @return Integer value between 0 and 3.
     */

    public static byte getRandomQuality(byte averageQuality, byte distanceBetweenRatesParameter) {
        byte initialQuality = averageQuality;

        byte logBase = distanceBetweenRatesParameter;
        byte maxQuality = 3;
        int r = RANDOM.nextInt((int)(pow(logBase,(maxQuality + 1))) - 1) + 1;

        int quality = maxQuality - (int)(log(r) / log(logBase));

        boolean negative = RANDOM.nextBoolean();
        if(negative) {
            initialQuality -= quality;
            initialQuality = (initialQuality < 0)? RandomGenerator.getRandomQuality(averageQuality, distanceBetweenRatesParameter): initialQuality;
        } else {
            initialQuality += quality;
            initialQuality = (initialQuality > 3)?  RandomGenerator.getRandomQuality(averageQuality, distanceBetweenRatesParameter) : initialQuality;
        }

        return initialQuality;
    }
}
