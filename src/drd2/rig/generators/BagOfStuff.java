package drd2.rig.generators;

import java.util.Arrays;
import java.util.Random;
/*

 */
public class BagOfStuff<T> {
    private final T[] stuff;
    private final double[] probabilities;
    private static final Random R = new Random(123);


    public BagOfStuff(T[] stuff, int[] rarities) {
        int rLen = rarities.length;
        int sLen = stuff.length;
        if(sLen != rLen) {
            System.err.println("Warning: BagOfStuff: The amount of probabilities and stuff is not the same! Trimming to min value.");
            int minlen = Math.min(sLen, rLen);
            this.stuff = Arrays.copyOfRange(stuff, 0, minlen);
            rarities = Arrays.copyOfRange(rarities, 0, minlen);
            this.probabilities = new double[minlen];
        } else {
            this.probabilities = new double[rLen];
            this.stuff = stuff;
        }
        calculateDistribution(rarities);


    }

    private void calculateDistribution(int[] rarities) {
        int sumOfRarities = 0;
        for (int r : rarities) {
            sumOfRarities += r;
        }
        int lastRarity = 0;
        for (int i = 1; i < rarities.length; ++i) {
            this.probabilities[i] = ((rarities[i] + lastRarity) / sumOfRarities);
            lastRarity += rarities[i];
        }
    }

    public T grabItem() {
        double prob = R.nextDouble();
        for (int i = 0; i < this.probabilities.length; i++) {
            if (probabilities[i] > prob) {
                return stuff[i];
            }
        }
        throw new RuntimeException("Error: BagOfItems: No item with probability greater than [" + prob + "].");
    }
}
