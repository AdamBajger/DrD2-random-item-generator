package drd2.rig.text.csv;

import drd2.rig.generators.BagOfStuff;
import drd2.rig.items.AbilityType;
import drd2.rig.items.Hands;
import drd2.rig.items.ItemAbility;
import drd2.rig.items.ItemBuilder;
import drd2.rig.items.WeaponBuilder;
import drd2.rig.items.WeaponType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CSVParser {
    private static final String SEPARATOR = ",";

    private static final String LABEL_NAME = "name";
    private static final String LABEL_WEAPON_TYPE = "weaponType";
    private static final String LABEL_HANDS = "hands";
    private static final String LABEL_COST = "cost";
    private static final String LABEL_RARITY = "rarity";
    private static final String LABEL_BONUS = "bonus";
    private static final String LABEL_DESCRIPTION = "description";


    /**
     * This method parses a CSV file with listed items and counts proper rarity of occurence
     * It will look on the column containing rarity and leave other values intact??
     * TODO: think through the concept of parsing items/weapons
     * @param csvFilePath path to the CSV parsed
     * @return array of items
     */
    public static BagOfStuff<WeaponBuilder> parseWeaponsFromCSV(String csvFilePath) {
        List<WeaponBuilder> weaponBuilderList = new LinkedList<>();
        List<Integer> rarityList = new LinkedList<>();

        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the headers of the table

            String[] splitHeader = line.split(SEPARATOR, -1);
            byte iType = findStrInArr(LABEL_WEAPON_TYPE, splitHeader);

            if (iType == -1) {
                throw new RuntimeException("Error: CSVParser: No item type column defined in CSV[" + csvFilePath + "].");
            }

            // this will read lines one by one until end of file and add Objects to list
            while ((line = br.readLine()) != null) {
                String[] itemInformation = line.split(SEPARATOR, -1);
                weaponBuilderList.add(
                        new ItemBuilder(
                                getValByLabel(splitHeader, itemInformation, LABEL_NAME),
                                Integer.parseInt(getValByLabel(splitHeader, itemInformation, LABEL_COST)),
                                new LinkedList<>(Collections.singletonList(new ItemAbility(AbilityType.SPECIAL, (byte)0,0,null, getValByLabel(splitHeader, itemInformation, LABEL_BONUS),null))),
                                getValByLabel(splitHeader, itemInformation, LABEL_DESCRIPTION)
                        ).toWeaponBuilder(
                                Hands.array[Integer.parseInt(getValByLabel(splitHeader, itemInformation, LABEL_HANDS))],
                                WeaponType.valueOf(getValByLabel(splitHeader, itemInformation, LABEL_WEAPON_TYPE).toUpperCase())
                        )
                );
                rarityList.add(Integer.parseInt(getValByLabel(splitHeader, itemInformation, LABEL_RARITY)));
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
        int[] rarityArray = new int[rarityList.size()];
        WeaponBuilder[] weaponBuilderArray = new WeaponBuilder[weaponBuilderList.size()];
        Iterator<Integer> ri = rarityList.iterator();
        Iterator<WeaponBuilder> wbi = weaponBuilderList.iterator();
        for (int i = 0; i < rarityList.size(); i++) {
            rarityArray[i] = ri.next();
            weaponBuilderArray[i] = wbi.next();
        }
        return new BagOfStuff<>(weaponBuilderArray, rarityArray);
    }

    /**
     * return index of string in an array.
     * If no such string is present, returns -1
     * @param str String to be searched for
     * @param haystack array of strings to be searched through
     * @return index of string if found, -1 otherwise
     */
    private static byte findStrInArr(String str, String[] haystack) {
        byte i = (byte)haystack.length;
        do {
            --i;
        } while (i >= 0 && !haystack[i].equals(str));
        return i;
    }

    private static String getValByLabel(String[] headers, String[] data, String label) {
        return data[findStrInArr(label, headers)];
    }
}
