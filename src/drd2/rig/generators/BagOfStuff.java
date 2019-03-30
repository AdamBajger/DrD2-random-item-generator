package drd2.rig.generators;

import java.util.Arrays;
import java.util.Random;
/*

 */
public class BagOfStuff<T> {
    private int maxProbability;
    private final T[] stuff;
    private final int[] probabilities;
    private static final Random R = new Random(123);


    /**
     * Creates a bag of objects assigned defined rarities.
     * We can grab objects from this bag and we get each object with a probability defined by rarities
     * The higher a number the higher the chance of getting the object.
     * For example with objects <b>[a, b, c]</b> and rarities <b>[10, 20, 40]</b> in a 700 grabs,
     * you should get cca 100x object <b>a</b>, cca 200x object <b>b</b> and cca 400x object <b>c</b>
     * If the number of probabilities is less than amount of objects in the bag, it will fill remaining values
     * with the median.
     * @param stuff objects to be stored in a bag
     * @param rarities array of hypothetical count of objects in a bag
     */
    public BagOfStuff(T[] stuff, int[] rarities) {
        this.stuff = Arrays.copyOf(stuff, stuff.length);
        int[] raritiesPadded = Arrays.copyOf(rarities, stuff.length);
        if(stuff.length > rarities.length) {
            for (int i = rarities.length; i < raritiesPadded.length; i++) {
                raritiesPadded[i] = rarities[(rarities.length/2)];
            }
        }
        this.probabilities = new int[stuff.length];
        calculateDistribution(raritiesPadded);
    }

    /**
     * Creates a bag of objects from which all have the same probability of getting grabbed.
     * @param stuff stuff to be stored in a bag
     */
    public BagOfStuff(T[] stuff) {
        int[] lmao = new int[stuff.length];
        for (int i = 0; i < stuff.length; i++) {
            lmao[i] = 1;
        }
        this.stuff = stuff;
        this.probabilities = new int[stuff.length];
        calculateDistribution(lmao);
    }

    private void calculateDistribution(int[] rarities) {
        int lastRarity = 0;
        for (int i = 1; i < rarities.length; ++i) {
            this.probabilities[i] = ((rarities[i] + lastRarity));
            lastRarity += rarities[i];
        }
        this.maxProbability = lastRarity;
    }

    public T grabStuff() {
        int prob = (int)(R.nextDouble() * (maxProbability + 1));
        for (int i = 0; i < this.probabilities.length; i++) {
            if (probabilities[i] > prob) {
                return stuff[i];
            }
        }
        throw new RuntimeException("Error: BagOfItems: No item with probability greater than [" + prob + "].");
    }
}
