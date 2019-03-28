package drd2.rig.text.csv;

import drd2.rig.items.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
    public static ArrayList<Item> parseItemsFromCSV(String csvFilePath) {
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the headers of the table

            byte iName;
            byte iItemType;
            byte iType;
            byte iHands;
            byte iCost;
            byte iRarity;
            byte iRegion;
            byte iBonus;
            byte iDescription;

            String[] splitRow = line.split(SEPARATOR, -1);
            for (byte i = 0; i < splitRow.length; i++) {
                switch (splitRow[i]) {
                    case LABEL_NAME:
                        iName = i;
                        break;
                    case LABEL_ITEM_TYPE:
                        iItemType = i;
                        break;
                    case LABEL_TYPE:
                        iType = i;
                        break;
                    case LABEL_HANDS:
                        iHands = i;
                        break;
                    case LABEL_COST:
                        iCost = i;
                        break;
                    case LABEL_RARITY:
                        iRarity = i;
                        break;
                    case LABEL_REGION:
                        iRegion = i;
                        break;
                    case LABEL_BONUS:
                        iBonus = i;
                        break;
                    case LABEL_DESCRIPTION:
                        iDescription = i;
                        break;
                }
            }


            // this will read lines one by one until end of file
            while ((line = br.readLine()) != null) {
                String[] itemInformation = line.split(SEPARATOR, -1);
                // TODO: dodělat generování objektů
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
}
