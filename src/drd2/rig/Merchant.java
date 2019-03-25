package drd2.rig;



import drd2.rig.generators.RandomGenerator;
import drd2.rig.items.AbilityType;
import drd2.rig.items.ItemAbility;
import drd2.rig.items.Weapon;
import drd2.rig.items.WeaponType;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Adam Václav Bajger on 10. 7. 2017
 */
public class Merchant implements java.io.Serializable {
    public static String DEFAULT_OBJ_STOR_DIR = "src/app/fuck/shit/";
    public static String DEFAULT_OBJ_NAME = "Default";

    public String name;
    private float priceM; // price multiplier. Every Merchant is somehow expensive
    // Instead of using associated array I use two arrays with associated values on corresponding indexes
    //private short[] weaponTypeFrequency;
    private WeaponType[] weaponTypesUsed;
    //private short weaponFrequencySum;
    private boolean sellingMagic;
    private float maxAverageRarity; // So you have something like "total rarity" but it scales with the number of items.
        // So for 4 items max rarity is 20 but for 6 items it is 30
    private float maxAverageAbilityRarity;
    private byte averageQuality;
    private Material[] materialsUsed; // Array containing specific amounts of Material elements representing the fractions of materials used.
    private AbilityType[] abilityTypesUsed; // Array containing specific amounts of AbilityType elements representing the fractions of ability types used.
    private byte itemAmount; // how many items merchant usually sells
    private byte itemAmountVariation; // by how many items the number of sold items can change.
        // i. e. if this is 5 and itemAmount is 7 ==> then the amount of sold items can vary from 2 to 12
    private byte weaponTypeVariation; // how much the merchant sticks to his favourite commodity.
        // the lower the value, the more he sells the same item types
        // for ex. with weaponTypeVariation = 0, then every time he has 30% of swords, 50% of daggers and 20% of axes
        // for ex. with weaponTypeVariation = 20, then every time he has different part of his goods as swords, daggers and so on
        // max value = 100 !!!!! exception needed!
        /*
        So it will have some ranges for particular weapon types (like on Freebitco.in roll)
        But when it will check the ranges, the amount of variation cannot exceed 100
        It is counted as percentage
         */
    private ArrayList<Weapon> currentGoods;


    Merchant(
            String name,
            //short[] weaponTypeFrequency,
            WeaponType[] weaponTypesUsed,
            boolean sellingMagic,
            float priceM,
            float maxAverageRarity,
            float maxAverageAbilityRarity,
            byte averageQuality,
            Material[] materialsUsed,
            AbilityType[] abilityTypesUsed,
            byte itemAmount,
            byte itemAmountVariation
            //byte weaponTypeVariation
    ) throws IllegalArgumentException {
        this.name = name;
        this.weaponTypesUsed = weaponTypesUsed;
        this.sellingMagic = sellingMagic;

        // check if following values are not negative
        try {
            this.priceM = 1;
            this.priceM = returnIfNotNegative(priceM, "priceM");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            this.maxAverageRarity = 2;
            this.maxAverageRarity = returnIfNotNegative(maxAverageRarity, "maxAverageRarity");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try{
            this.maxAverageAbilityRarity = 2;
            if(maxAverageAbilityRarity >= ItemAbility.MIN_RARITY_VALUE) {
                this.maxAverageAbilityRarity = maxAverageAbilityRarity;
            } else {
                throw new IllegalArgumentException("Parameter maxAverageAbilityRarity must be at least " + ItemAbility.MIN_RARITY_VALUE + ". Your input: " + maxAverageAbilityRarity + ". \n Value set to " + ItemAbility.MIN_RARITY_VALUE + ". ");
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            this.averageQuality = 0;
            if(averageQuality > 3) {
                throw new IllegalArgumentException("Maximum quality is 3. Your input was " + averageQuality + ", averageQuality set to 0.");
            }
            this.averageQuality = returnIfNotNegative(averageQuality, "averageQuality");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            if(materialsUsed.length == 0) {
                this.materialsUsed = new Material[]{Material.IRON};
                throw new IllegalArgumentException("Array materialsUsed cannot be empty. Array set to Material[]{Material.IRON}");
            } else {
                this.materialsUsed = materialsUsed;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            if(abilityTypesUsed.length == 0) {
                this.abilityTypesUsed = new AbilityType[]{AbilityType.BONUS, AbilityType.ACTIVATE, AbilityType.EXHAUSTION};
                throw new IllegalArgumentException("Array abilityTypesUsed cannot be empty. Array set to abilityTypesUsed[]{AbilityType.BONUS, AbilityType.ACTIVATE, AbilityType.EXHAUSTION}");
            } else {
                this.abilityTypesUsed = abilityTypesUsed;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            this.itemAmount = 0;
            this.itemAmount = returnIfNotNegative(itemAmount, "itemAmount");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            this.itemAmountVariation = 0;
            if(itemAmountVariation > itemAmount) {
                throw new IllegalArgumentException("itemAmountVariation cannot be greater than itemAmount. itemAmountVariation set to 0.");
            }
            this.itemAmountVariation = returnIfNotNegative(itemAmountVariation, "itemAmountVariation");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            if(itemAmount + itemAmountVariation > Byte.MAX_VALUE) {
                this.itemAmount = 10;
                this.itemAmountVariation = 3;
                throw new IllegalArgumentException("Sum of itemAmount and itemAmountVariation cannot exceed " + Byte.MAX_VALUE + "itemAmount set to 10, itemAmountVariation set to 3.");
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            this.weaponTypeVariation = 0;
            if (weaponTypeVariation > 100) {
                throw new IllegalArgumentException("weaponTypeVariation cannot exceed 100");
            }
            this.weaponTypeVariation = returnIfNotNegative(weaponTypeVariation, "weaponTypeVariation");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    Merchant() {
        // Default materials used by blacksmiths
        Material[] m = new Material[]{Material.BRONZE,
                Material.STEEL,
                Material.STEEL,
                Material.STEEL,
                Material.IRON,
                Material.IRON,
                Material.IRON,
                Material.IRON,
                Material.IRON,
                Material.IRON,
                Material.IRON,
                Material.BRONZE,
                Material.BRONZE,
                Material.BONE,
                Material.OBSIDIAN,
                Material.WOOD
        };
        //System.out.println(Merchant.DEFAULT_OBJ_NAME);

        this.name = Merchant.DEFAULT_OBJ_NAME;
        this.weaponTypesUsed = new WeaponType[]{WeaponType.SWORD, WeaponType.EASY, WeaponType.HAMMER, WeaponType.DAGGER, WeaponType.AXE};
        this.sellingMagic = true;
        this.priceM = (float)20;
        this.maxAverageRarity = (float)10;
        this.maxAverageAbilityRarity = (float)12;
        this.averageQuality = (byte)2;
        this.materialsUsed = m;
        this.abilityTypesUsed = new AbilityType[]{AbilityType.BONUS, AbilityType.EXHAUSTION};
        this.itemAmount = (byte)20;
        this.itemAmountVariation = (byte)5;

    }

    private int returnIfNotNegative(int value, String variableName) {
        if(value < 0 ) {
            throw new IllegalArgumentException("This variable (" + variableName + ") cannot be negative. Value set to 0");
        } else return value;
    }
    private short returnIfNotNegative(short value, String variableName) {
        if(value < 0 ) {
            throw new IllegalArgumentException("This variable (" + variableName + ") cannot be negative. Value set to 0");
        } else return value;
    }
    private byte returnIfNotNegative(byte value, String variableName) {
        if(value < 0 ) {
            throw new IllegalArgumentException("This variable (" + variableName + ") cannot be negative. Value set to 0");
        } else return value;
    }
    private float returnIfNotNegative(float value, String variableName) {
        if(value < 0 ) {
            throw new IllegalArgumentException("This variable (" + variableName + ") cannot be negative. Value set to 0");
        } else return value;
    }

    public void createSortiment(String region) {
        //adds or subtracts random number up to itemAmountVariation
        byte currentItemAmount = itemAmount;
        try {
            currentItemAmount = (byte) (itemAmount + (itemAmountVariation - (2*(RandomGenerator.RANDOM.nextInt(itemAmountVariation)))));
        } catch(IllegalArgumentException e) {
            //e.printStackTrace();
        }
        Weapon[] sortiment = new Weapon[currentItemAmount];

        //System.out.println(Arrays.toString(weaponTypeFrequency));

        // for every weapon type frequency it will trigger a loop, in which it creates all the weapons of particular type
        short h = 0;
        float leftoverBaseRarity = 0; // you have average rarity, but the generator generates weapons of lower rarity.
        float leftoverAbilityRarity = 0; // same as above, but for abilities
        // The rarity, that is left, is used in next generation, so it won't just get lost

        for(short i = 0; i < currentItemAmount; i++) {
            //for(short j = 0; j < ((weaponTypeFrequency[i]*currentItemAmount/weaponFrequencySum)); j++) {

            leftoverBaseRarity += maxAverageRarity; // charge up the leftoverBaseRarity storage
            leftoverAbilityRarity += maxAverageAbilityRarity;
            // generate a new weapon.
            sortiment[h] = PickRandomOLD.weaponOfType(
                    leftoverBaseRarity,
                    weaponTypesUsed[RandomGenerator.RANDOM.nextInt(weaponTypesUsed.length)],
                    RandomGenerator.getRandomQuality(averageQuality),
                    materialsUsed[RandomGenerator.RANDOM.nextInt(materialsUsed.length)]
            );

            // edit the rarity and price based on material used, no one cares that it will fuck up the system
            sortiment[h].setPrice((int)(sortiment[h].getPrice() * sortiment[h].getMaterial().priceMultiplier));

            // after item is generated, its rarity is decremented from leftover rarity
            leftoverBaseRarity -= sortiment[h].getBaseRarity();

            // If the merchant sells magic items, give them some magic (also apply ability rarity changes
            if(sellingMagic) {
                // Takes random ability and adds it to the ItemAbility[] array of currently generated weapon
                ItemAbility randomAbility = PickRandomOLD.GeneralAbility(leftoverAbilityRarity, region);
                sortiment[h].addAbility(randomAbility);
                leftoverAbilityRarity -= randomAbility.getRarity();

                // It will add multiple abilities sometimes (and if there is enough unused rarity)
                while(leftoverAbilityRarity >= ItemAbility.MIN_RARITY_VALUE && RandomGenerator.RANDOM.nextBoolean()) {
                    if(!sortiment[h].getAbilities().contains(randomAbility = PickRandomOLD.GeneralAbility(leftoverAbilityRarity, region))) {
                        sortiment[h].addAbility(randomAbility);
                        leftoverAbilityRarity -= randomAbility.getRarity();
                    }
                }
            }


            h++;
            //}


        }
        // If there are some empty places in the Weapon[] sortiment array, fill them with random items (without really applying previous rules)
        byte missingItemCount = (byte) (sortiment.length - (h)); // Explanation below >>
        // Maybe you thint that instead of "h" there could be "(h + 1)". But "h" is always increased after an item is generated.
        // So (beginning with "h" = 0) after 3 iterations "h" will be 3, not 2. The last value of "h" will not be used as index.
        for (int i = 0; i < missingItemCount; i++) {
            sortiment[h] = PickRandomOLD.weaponOfType(
                    maxAverageRarity,
                    weaponTypesUsed[RandomGenerator.RANDOM.nextInt(weaponTypesUsed.length)],
                    RandomGenerator.getRandomQuality(averageQuality),
                    materialsUsed[RandomGenerator.RANDOM.nextInt(materialsUsed.length)]
            );
            h++;
        }


        this.currentGoods = new ArrayList<>(Arrays.asList(sortiment));
        //System.out.println(Arrays.toString(sortiment));
        Collections.sort(this.currentGoods); // sort the items out
        //System.out.println(Arrays.toString(sortiment));
    }

    public ArrayList<Weapon> getCurrentGoods() {
        return currentGoods;
    }

    public float getPriceM() {
        return priceM;
    }

    public WeaponType[] getWeaponTypesUsed() {
        return weaponTypesUsed;
    }

    public boolean isSellingMagic() {
        return sellingMagic;
    }

    public float getMaxAverageRarity() {
        return maxAverageRarity;
    }

    public float getMaxAverageAbilityRarity() {
        return maxAverageAbilityRarity;
    }

    public byte getAverageQuality() {
        return averageQuality;
    }

    public Material[] getMaterialsUsed() {
        return materialsUsed;
    }

    public AbilityType[] getAbilityTypesUsed() {
        return abilityTypesUsed;
    }

    public byte getItemAmount() {
        return itemAmount;
    }

    public byte getItemAmountVariation() {
        return itemAmountVariation;
    }

    public byte getWeaponTypeVariation() {
        return weaponTypeVariation;
    }

    public String sortimentToString() {
        String plainTextSortiment = "Merchant{" +
                "currentGoods: \n";

        for (Weapon w : currentGoods) {
            plainTextSortiment = plainTextSortiment + w.toString() + "\n";
        }
        return plainTextSortiment;
    }

    public String sortimentToSimplifiedString() {
        String plainTextSortiment = name + "{\n";
        for (Weapon w : currentGoods) {
            plainTextSortiment = plainTextSortiment + w.name + ",\n";
        }
        plainTextSortiment = plainTextSortiment + "}";
        return plainTextSortiment;
    }

    /**
     * Serializes current merchant and saves it as a file named by the name of the merchant and {@code System#currentTimeMillis()} to a given directory.
     * @param directory Target directory, where the file is saved to. Written in the format "dir1/dir2/dir3/".
     * @throws java.io.IOException
     */
    public void storeObjectToFile(String directory) throws java.io.IOException {
        // Write to disk with FileOutputStream
        FileOutputStream fileOut = new FileOutputStream(directory + this.name + ".merchant");

        // Write object with ObjectOutputStream
        ObjectOutputStream objOut = new ObjectOutputStream (fileOut);

        // Write object out to disk
        objOut.writeObject(this);
    }

    /**
     * Default version of {@link Merchant#storeObjectToFile(String)}. It uses the same directory as the class is in.
     * @throws java.io.IOException
     */
    public void storeObjectToFile() throws java.io.IOException {
        storeObjectToFile(Merchant.DEFAULT_OBJ_STOR_DIR);
    }

    public static Merchant loadObjectFromFile(String path) throws IOException {
        Object obj = null;
        try {
            // Read from disk using FileInputStream
            FileInputStream f_in = new FileInputStream(path);

            // Read object using ObjectInputStream
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object
            obj = obj_in.readObject();

        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (obj instanceof Merchant) {
            // Cast object to a Merchant and return
            return (Merchant) obj;
        } else {
            throw new IOException("File was not loaded. (" + path + ")");
        }

    }


}
