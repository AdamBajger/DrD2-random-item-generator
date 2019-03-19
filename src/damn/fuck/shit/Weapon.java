package damn.fuck.shit;

import java.util.LinkedList;

/**
 * Created by Admin on 30.06.2017.
 */
public class Weapon /*extends Item*/ implements java.io.Serializable, java.lang.Comparable<Weapon>{
    String name;
    private WeaponType weaponType;
    private String weaponCharacteristics;
    private Hands hands;
    private byte quality;
    private Material material;
    private int price;
    private float rarity;
    private LinkedList<ItemAbility> abilities;
    private String lore;


    public Weapon(
            String name,
            WeaponType weaponType,
            String weaponCharacteristics,
            Hands hands,
            byte quality,
            Material material,
            int price,
            float rarity,
            LinkedList<ItemAbility> abilities, // !!!!! there should be list, not array. Need the push function
            String lore
    ) {
        this.name = name;
        this.weaponType = weaponType;
        this.hands = hands;
        this.weaponCharacteristics = weaponCharacteristics;
        this.quality = quality;
        this.material = material;
        this.price = price;
        this.rarity = rarity;
        this.abilities = abilities;
        this.lore = lore;
    }

    public float getTotalRarity() {
        float totalRarity = rarity*quality*material.rarityMultiplier;
        for (ItemAbility o: abilities) {
            totalRarity += o.getRarity();
        }
        return totalRarity;
    }

    public float getBaseRarity() {
        return rarity;
    }

    public void setQuality(byte quality) {
        this.quality = quality;
    }

    public LinkedList<ItemAbility> getAbilities() {
        return abilities;
    }

    public void addAbility(ItemAbility ability) {
        this.abilities.add(ability);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRarity() {
        return rarity;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "name: " + name +
                ", weaponType=" + weaponType + ",\n" +
                "weaponCharacteristics: " + weaponCharacteristics + ",\n" +
                "hands=" + hands + ", quality=" + quality + ", material=" + material + ", price=" + price + ", rarity=" + rarity + ",\n" +
                "abilities=" + abilities.toString() + "\n" +
                ", lore='" + lore + '\'' +
                '}' + "\n";
    }

    /**
     * This method returns value based on input nubers. Zero means that those numbers are the same, positive number means that the second number is lower, negative value means the opposite.
     * @param first some word represented by array of characters
     * @param second another word represented by array of characters
     * @return Negative, positive value or zero.
     */

    private byte compareTwoNames(char[] first, char[] second, byte index) {
        try {
            if ((CzechAlphabetEnum.valueOf(String.valueOf(first[index])).ordinal() - CzechAlphabetEnum.valueOf(String.valueOf(second[index])).ordinal()) == 0) {
                return compareTwoNames(first, second, (byte)(index + 1));
            } else {
                return (byte)(CzechAlphabetEnum.valueOf(String.valueOf(first[index])).ordinal() - CzechAlphabetEnum.valueOf(String.valueOf(second[index])).ordinal());
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            return (first.length < second.length ? (byte)127 : (byte)(-128));
        }
    }

    @Override
    public int compareTo(Weapon input) {
        // compare based on type, if it is different
        if(input.weaponType != this.weaponType) {
            return this.weaponType.ordinal() - input.weaponType.ordinal();
        }
        // If the weapon types are the same, continue in name comparison
        // set up two character arrays from compared names
        char[] thisName = this.name.replaceAll("\\s","").toCharArray();
        char[] inputName = input.name.replaceAll("\\s","").toCharArray();

        // Return the final result of comparison obtained from the compareTwoNames() method
        return compareTwoNames(thisName, inputName, (byte)0);
    }
}
