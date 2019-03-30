package drd2.rig.text.csv;

import drd2.rig.Material;
import drd2.rig.generators.BagOfStuff;
import drd2.rig.items.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static drd2.rig.items.ItemType.WEAPON;

public class CSVParser {
    public static final String SEPARATOR = ",";

    public static final String LABEL_NAME = "name";
    public static final String LABEL_ITEM_TYPE = "itemType";
    public static final String LABEL_TYPE = "type";
    public static final String LABEL_HANDS = "hands";
    public static final String LABEL_COST = "cost";
    public static final String LABEL_RARITY = "rarity";
    public static final String LABEL_REGION = "region";
    public static final String LABEL_BONUS = "bonus";
    public static final String LABEL_DESCRIPTION = "description";


    /**
     * This method parses a CSV file with listed items and counts proper rarity of occurence
     * It will look on the column containing rarity and leave other values intact??
     * TODO: think through the concept of parsing items/weapons
     * @param csvFilePath path to the CSV parsed
     * @return array of items
     */
    public static BagOfStuff<WeaponBuilder> parseWeaponsFromCSV(String csvFilePath) {
        Set<WeaponBuilder> set = new HashSet<>();
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the headers of the table

            String[] splitHeader = line.split(SEPARATOR, -1);
            byte iType = findStrInArr(LABEL_TYPE, splitHeader);

            if (iType == -1) {
                throw new RuntimeException("Error: CSVParser: No item type column defined in CSV[" + csvFilePath + "].");
            }


            // this will read lines one by one until end of file
            while ((line = br.readLine()) != null) {
                String[] itemInformation = line.split(SEPARATOR, -1);
                set.add(new ItemBuilder(
                        getValByLabel(splitHeader, itemInformation, "name"),
                        Integer.parseInt(getValByLabel(splitHeader, itemInformation, LABEL_COST)),
                        new LinkedList<>(Arrays.asList(new ItemAbility[]{new ItemAbility(AbilityType.SPECIAL, (byte)0,0,null, getValByLabel(splitHeader, itemInformation, "bonus"),null)})),
                        getValByLabel(splitHeader, itemInformation, LABEL_DESCRIPTION)
                ))


                WeaponType.valueOf(getValByLabel(splitHeader, itemInformation, "type")),
                        Hands.array[Integer.parseInt(getValByLabel(splitHeader, itemInformation, "hands"))],
                        Byte.parseByte(getValByLabel(splitHeader, itemInformation, "quality")),
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
        return null;
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
