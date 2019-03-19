package damn.fuck.shit;

import java.lang.IllegalArgumentException;
import java.util.Random;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class RandomGenerator {
    //Random RANDOM = new Random();
    //private double maxRarity = 20;  // maximum available rarity

    public static Random RANDOM = new Random();


    /**
     * This is the default version of {@link RandomGenerator#getRandomRarity(short)}.
     * The optional maxRarity parameter is set to 20.
     * @see RandomGenerator#getRandomRarity(short).
     */
    public static double getRandomRarity() {
        return getRandomRarity((short)20);
    }

    /**
     * This method gives you random rarity from 0 to your specified value (maxRarity) from a static context.
     * Values are distributed logarithmically, so the higher values are more rare, than lower values.
     * @param maxRarity integer value representing maximum possible rarity output.
     * @return random double value
     */
    public static double getRandomRarity(short maxRarity) {
       return getRandomRarity(maxRarity, (short)0);
    }

    public static double getRandomRarity(short maxRarity, short minRarity) throws IllegalArgumentException {
        double rarityBase = 4;
        double logBase = 4;
        double randomValueMax = (Math.pow(logBase,rarityBase));
        double randomValueMin = 1/randomValueMax;

        if(maxRarity < minRarity){
            throw new IllegalArgumentException("Maximum can not be lower than minimum. Max = " + maxRarity + " Min = " + minRarity + ". ");
        }



        double pls = RANDOM.nextDouble();
        if (pls < randomValueMin) {
            pls = randomValueMin;
            //System.out.println("pls reduced to " + randomValueMin);
        }
        // this part is important --> maybe you do not understand it, but you did it for purpose
        maxRarity = (short)(maxRarity - minRarity);
        /*double outputRarity = */ return minRarity + maxRarity - (((Math.log(pls*randomValueMax)/Math.log(logBase))) * (maxRarity / rarityBase));
        //return outputRarity;
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
        //System.out.println("Random quality is " + quality);

        boolean negative = RANDOM.nextBoolean();
        if(negative) {
            initialQuality -= quality;
            initialQuality = (initialQuality < 0)? /*(byte)RANDOM.nextInt(3)*/ RandomGenerator.getRandomQuality(averageQuality, distanceBetweenRatesParameter): initialQuality;
        } else {
            initialQuality += quality;
            initialQuality = (initialQuality > 3)? /*(byte)RANDOM.nextInt(3)*/ RandomGenerator.getRandomQuality(averageQuality, distanceBetweenRatesParameter) : initialQuality;
        }

        return initialQuality;
    }
}
