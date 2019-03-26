package drd2.rig.text.csv;

import drd2.rig.items.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CSVItemParser {
    public static final String SEPARATOR = ",";

    public static final String LABEL_RARITY = "rarity";


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
            // find out which column contains rarity

            // TODO: implement switch to parse headers
            String[] splitRow = line.split(SEPARATOR, -1);
            byte rarityI = getIndexOfStrInArr(splitRow, LABEL_RARITY); // TODO: add constants strings


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

    private static byte getIndexOfStrInArr(String[] haystack, String needle) {
        byte rarityColIndex = 0;
        for (String s : haystack) {
            // if we reach end before finding the string
            if (s.length() <= rarityColIndex) {
                System.err.println("Missing Rarity column.");
                return -1;
            }
            if (s.equals("rarity")) {
                break;
            } else rarityColIndex++;
        }
        return rarityColIndex;
    }
}
