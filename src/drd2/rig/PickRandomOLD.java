package drd2.rig;

import drd2.rig.generators.RandomGenerator;
import drd2.rig.items.*;

import java.io.*;
import java.lang.ArrayIndexOutOfBoundsException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static drd2.rig.ResourceTypes.BODY;
import static drd2.rig.ResourceTypes.SOUL;

/**
 * Created by Adam Bajger on 16.06.2017.
 * This class reads the CSV item database.
 * It picks random line based on rarity column and computes ability properties based on information stored there
 */

@Deprecated
public class PickRandomOLD {
    // SETUP for getting random value from enum AbilityType
    private static final List<AbilityType> VALUES = Collections.unmodifiableList(Arrays.asList(AbilityType.values()));
    private static final List<WeaponType> ATTACK_TYPE = Collections.unmodifiableList(Arrays.asList(WeaponType.values()));

    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random(); // use this random primary

    /**
     * This is the default version of {@link PickRandomOLD#GeneralAbility} with region parameter and AbilityType parameter omitted.
     * @see PickRandomOLD#GeneralAbility(double, String, AbilityType)
     */
    public static ItemAbility GeneralAbility(double maxRarity) {
        if(RANDOM.nextBoolean()) {
            return GeneralAbility(maxRarity, null, AbilityType.EXHAUSTION);
        } else {
            return GeneralAbility(maxRarity, null, AbilityType.BONUS);
        }

    }

    /**
     * This is the default version of {@link PickRandomOLD#GeneralAbility} with AbilityType parameter omitted.
     * @see PickRandomOLD#GeneralAbility(double, String, AbilityType)
     */
    public static ItemAbility GeneralAbility(double maxRarity, String region) {
        if(RANDOM.nextBoolean()) {
            return GeneralAbility(maxRarity, region, AbilityType.EXHAUSTION);
        } else {
            return GeneralAbility(maxRarity, region, AbilityType.BONUS);
        }

    }

    /**
     * This is a version of {@link PickRandomOLD#GeneralAbility} with region parameter omitted.
     * @see PickRandomOLD#GeneralAbility(double, String, AbilityType)
     */
    public static ItemAbility GeneralAbility(double maxRarity, AbilityType abilityType) {
        return GeneralAbility(maxRarity, null, abilityType);
    }

    /**
     * This method picks random ability from abilitiesGeneral.csv and computes detailed parameters for that ability.
     * @param maxRarity Specifies the maximum rarity allowed for an ability.
     * @param region Region in which are you standing, when shopping, affects occurrence of different abilities.
     * @param abilityType Decides the type of ability that will be generated. This method allows only {@code AbilityType.EXHAUSTION} and {@code AbilityType.BONUS}
     * @return ItemAbility object
     */
    // !!!!! change this class that it will generate also other ability types.
    // It will only switch CSV file path at the beginning and then some different stuff ...
    public static ItemAbility GeneralAbility(double maxRarity, String region, AbilityType abilityType) throws IllegalArgumentException {
        if(abilityType != AbilityType.BONUS && abilityType != AbilityType.EXHAUSTION) {
            System.out.println("This method only allows " + AbilityType.BONUS + " and " + AbilityType.EXHAUSTION + " as abilityType (for now). \nParameter abilityType was changed randomly to one of these two.");
            if(RANDOM.nextBoolean()) {
                return GeneralAbility(maxRarity, region, AbilityType.EXHAUSTION);
            } else {
                return GeneralAbility(maxRarity, region, AbilityType.BONUS);
            }
        }
        String csvFilePath = "src\\app\\fuck\\shit\\abilitiesGeneral.csv";
        BufferedReader br = null;
        String line; //= "";
        String[] content; // Array containing particular values stored in single CSV line
        String cvsSplitBy = ";"; // Using semicolon as a "comma" SEPARATOR while parsing CSV file
        String circumstances = "";
        Double currentAbilityRarity = 0.0; // this variable holds ability rarity of current line
        double abilityPrice = 0.0; // this holds the price of current ability
        Set<String> commonRegions; // in which regions are these abilities common
        Set<String> uncommonRegions; // in which regions are those abilities uncommon
        boolean abilityPicked = false;
        byte bonusLevel = 0;

        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the infoline of the table
            // find out the number of lines with abilities in the table. Loaded from infoline
            int numberOfLines = Integer.parseInt(line.split(";")[1]);
            line = br.readLine(); // load the headline so it wont bother the algorithm
            // get random starting line.
            int initialLine = RANDOM.nextInt(numberOfLines);
            // get to the randomly picked line
            for (int i = 0; i < initialLine; i++) {
                br.readLine();
            }




            // this will read lines one by one until end of file
            findingAbility:
            while ((line = br.readLine()) != null) {
                int plx = RANDOM.nextInt(1 + numberOfLines/5); // pick random number of lines to skip
                // actually loop over those "skipped" lines
                for(int pls = 0; pls < plx; pls++) {
                    // read next line and if it went ok, do stuf
                    if((line = br.readLine()) != null) {
                        // it's a 50% chance that it will check for common abilities
                        // common abilities have halved its rarity
                        if (RANDOM.nextBoolean()) {
                            content = line.split(cvsSplitBy, -1); // get the array of values in line
                            try {
                                // get the set of regions where the ability is common
                                currentAbilityRarity = Double.parseDouble(content[1]);
                                abilityPrice = Double.parseDouble(content[2]);
                                commonRegions = new HashSet<>(Arrays.asList(content[3].split(",", -1)));

                                if (region != null && commonRegions.contains(region)) {
                                    if (currentAbilityRarity / 2 < maxRarity) {
                                        // here you check the abilityPicked = true and change the price and rarity
                                        abilityPicked = true;
                                        abilityPrice *= (RANDOM.nextDouble() * 0.5) + 0.5; // decrease price by up to 50%
                                        //System.out.println("Loop broke.");
                                        break findingAbility;
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (!abilityPicked && line != null) {
                    content = line.split(cvsSplitBy, -1);
                    try {
                        circumstances = content[0];
                        currentAbilityRarity = Double.parseDouble(content[1]);
                        abilityPrice = Double.parseDouble(content[2]);
                        commonRegions = new HashSet<>(Arrays.asList(content[3].split(",", -1)));
                        uncommonRegions = new HashSet<>(Arrays.asList(content[4].split(",", -1)));
                        if(region != null) {
                            if (uncommonRegions.contains(region)) {
                                currentAbilityRarity *= 1.5;
                            } else if (commonRegions.contains(region)) {
                                currentAbilityRarity *= 0.5;
                            }
                        }

                        //check if ability matches the maxRarity
                        if (currentAbilityRarity < maxRarity) {
                            // here you check the abilityPicked = true and do stuff
                            abilityPicked = true;
                            abilityPrice *= ((RANDOM.nextDouble() * 0.5) + 1); // increase price up to 1.5x
                            //System.out.println("Loop broke.");
                            break;
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        // check if ability was picked abilityPicked = true
        // if so, the you finish the ability picking (set level of the bonus)
        // then you save it all (final rarity, price, level, circumstances) to a returnArray
        if (abilityPicked) {

            byte randomBonusLevel = (byte)(RANDOM.nextInt(4) + 1);
            double maxBonusLevel = maxRarity / currentAbilityRarity;
            if (randomBonusLevel > maxBonusLevel) {
                bonusLevel = (byte) maxBonusLevel;
            } else {
                bonusLevel = randomBonusLevel;
            }
        }

        float totalRarity = (float)(bonusLevel * currentAbilityRarity); // total rarity
        int totalPrice = (int)(bonusLevel * abilityPrice); // total price
        return new ItemAbility(abilityType, circumstances, bonusLevel, totalPrice, totalRarity, BODY);
    }

    public static ItemAbility ActivatedAbility() {
        // TODO: Je třeba dodělat metodu na generování ostatních schopností
        return new ItemAbility(
                AbilityType.ACTIVATE,
                "Toto je testovací schopnost. Stojí tě dvě duše a nic se nestane.",
                (byte)(1),
                24,
                (float)5.7,
                SOUL
        );
    }

    /**
     * This is a {@link PickRandomOLD#weaponOfType(double, WeaponType, byte, Material)} version, with some default values.
     * @param weaponType specifies the type of weapon picked. Also determines the CSV table file, from which it will pick the weapon
     * @param material material of the weapon
     * @return new {@link Weapon} object
     * @see PickRandomOLD#weaponOfType(double, WeaponType, byte, Material)
     */
    public static Weapon weaponOfType(WeaponType weaponType, Material material) {
        return weaponOfType(RandomGenerator.getRandomRarity((short)(7)) + 1, weaponType, (byte)0, material);
    }

    /**
     * This will pick random weapon from the table of weapons. Table is chosen by type, you specify by the weaponType parameter.
     * The chosen weapon is determined by rarity. More rare weapons are picked less frequently.
     * For better understanding of how rarity is generated see {@link RandomGenerator#getRandomRarity()}.
     * @param maxRarity determines the maximum rarity a single weapon can have. Value must be at least 1. If less, exception will be thrown.
     * @param weaponType each {@link WeaponType} has its own CSV table of weapons.
     * @param weaponQuality sets the quality for picked weapon.
     * @param weaponMaterial decides from which material the weapon will be made.
     * @return new {@link Weapon} object.
     */
    public static Weapon weaponOfType(double maxRarity, WeaponType weaponType, byte weaponQuality, Material weaponMaterial) throws IllegalArgumentException {
        if(maxRarity < 1) {
            throw new IllegalArgumentException("Maximum rarity must be at least 1. maxRarity = " + maxRarity + ". ");
        }
        String csvFilePath = "src\\app\\fuck\\shit\\" + weaponType + ".csv";
        BufferedReader br = null;
        String line; //= "";
        String[] content; // Array containing particular values stored in single CSV line
        String csvSplitBy = ";"; // Using semicolon as a "comma" SEPARATOR while parsing CSV file
        String weaponName = "";
        Hands hands = Hands.NO_HANDS;
        float currentWeaponRarity = 0; // this variable holds ability rarity of current line
        float weaponPrice = 0; // this holds the price of current ability
        String bonuses = "";
        String lore = "";
        boolean weaponPicked = false;

        //BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the infoline of the table and use it
            // find out the number of lines with abilities - from the infoline
            System.out.println(line);
            short numberOfLines = Short.parseShort(line.split(csvSplitBy)[1]);
            //Now load the headline. That is enough to push it aside from the algorithm.
            line = br.readLine(); //zbraň;ruce;cena;rarita;kraj původu;typ;výhody;příběh


            // get random starting line.
            short initialLine = (short)RANDOM.nextInt(numberOfLines);
            // get to the randomly picked line
            for (short i = 0; i < initialLine; i++) {
                br.readLine();
            }
/*

            //Specify the output file name and path here
            File file = new File("src\\app\\fuck\\shit\\TEST.csv");
            if (!file.exists()) {
                try {
                    if(!file.createNewFile()) {
                        System.out.println("Soubor nevytvořen");
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            //creating the buffered writer for file output stream
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
*/

            // this will read lines one by one until end of file
            while ((line = br.readLine()) != null) {
                short skippedLines = (short)RANDOM.nextInt(1 + (numberOfLines/5)); // pick random number of lines to skip
                // actually loop over those "skipped" lines
                for(short pls = 0; pls < skippedLines; pls++) {
                    // read next line and if it went ok, do stuff
                    if((line = br.readLine()) == null) {
                        // If there are no lines left, and weapon is not picked -->
                        // start over (recursive solution)
                        return PickRandomOLD.weaponOfType(maxRarity, weaponType, weaponQuality, weaponMaterial);
                    }
                }
                // process the next line that is picked
                content = line.split(csvSplitBy, -1);
                try {
                    // load information from finally picked line
                    //zbraň;ruce;cena;rarita;kraj původu;typ;výhody;příběh
                    weaponName = content[0];
                    hands = Hands.array[Integer.parseInt(content[1])];
                    weaponPrice = Float.parseFloat(content[2]);
                    currentWeaponRarity = Float.parseFloat(content[3]);
                    weaponType = WeaponType.valueOf(content[5]);
                    bonuses = content[6];
                    lore = content[7];
/*
                    //write the weaponName to the TEST.csv
                    try {
                        bw.write(weaponName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
*/
                    //check if ability matches the maxRarity
                    if (currentWeaponRarity < maxRarity) {
                        // here you check the weaponPicked = true
                        // then you break the main while loop and proceed to weapon returning
                        weaponPicked = true;
                        //System.out.println("Loop broke.");
                        break;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Wrong number format" + csvFilePath);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

/*
            if(bw != null) {
                try {
                    bw.close();
                } catch(Exception ex){
                    System.out.println("Error in closing the BufferedWriter"+ex);
                    ex.printStackTrace();
                }
            }
*/
        }

        // check if weapon was picked (weaponPicked == true)
        // if so, then you finish the weapon picking (set level of the quality, material)
        // then you save it all (final rarity, price, quality, weaponName, and so on) to a returnArray
        if (weaponPicked) {
            float totalRarity = (weaponQuality * currentWeaponRarity); // total rarity
            int totalPrice = (int)(weaponQuality * weaponPrice); // total price
            return new Weapon(
                    weaponName,
                    weaponType,
                    bonuses,
                    hands,
                    weaponQuality,
                    weaponMaterial,
                    totalPrice,
                    totalRarity,
                    new LinkedList<ItemAbility>(),
                    lore
            );
        } else {
            System.out.println("One level deeper in recursion...");
            return weaponOfType(maxRarity, weaponType, weaponQuality, weaponMaterial);
        }

    }


    public static WeaponType BasicWeaponType() {
        // false = one-handed, TRUE = two-handed
        // just picks random weapon type
        switch(RANDOM.nextInt(WeaponType.values().length)) {
            case 0:
                return WeaponType.AXE;
            case 1:
                return WeaponType.DAGGER;
            case 2:
                return WeaponType.EASY;
            case 3:
                return WeaponType.HAMMER;
            case 4:
                return WeaponType.RANGED;
            case 5:
                return WeaponType.SWORD;
            default:
                return WeaponType.EASY;
        }
    }
}
