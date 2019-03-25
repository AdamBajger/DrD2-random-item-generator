package drd2.rig.csv;

import drd2.rig.items.Item;
import drd2.rig.items.ItemType;
import drd2.rig.items.WeaponType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSVItemParser {
    static String separator = ",";


    /**
     * This method parses a CSV file with listed items and counts proper rarity of occurence
     * It will look on the column containing rarity and leave other values intact??
     * TODO: think through the concept of parsing items/weapons
     * @param csvFilePath path to the CSV parsed
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Item> parseItemsFromCSV(String csvFilePath) throws FileNotFoundException {
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
            line = br.readLine(); // load the headers of the table
            // find out which column contains rarity
            byte rarityColIndex = 0;
            for (String s : line.split(separator, -1)) {
                if (s.equals("rarity")) {
                    break;
                } else rarityColIndex++;
            }

            // this will read lines one by one until end of file
            while ((line = br.readLine()) != null) {
                String[] itemInformation = line.split(separator, -1);
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
