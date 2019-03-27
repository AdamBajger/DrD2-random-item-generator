package drd2.rig.items;

import drd2.rig.Material;

import java.util.LinkedList;

public abstract class Item implements java.io.Serializable, java.lang.Comparable<Item> {
    private final String name;
    private final byte quality;
    private final Material material;
    private final int basicPrice;
    private final LinkedList<ItemAbility> abilities;
    private final String description;

    public abstract int getOrdinal();

    public Item(String name,
                byte quality,
                Material material,
                int basicPrice,
                LinkedList<ItemAbility> abilities,
                String description) {
        this.name = name;
        this.quality = quality;
        this.material = material;
        this.basicPrice = basicPrice;
        this.abilities = abilities;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public byte getQuality() {
        return quality;
    }

    public Material getMaterial() {
        return material;
    }

    public int getPrice() {
        return (int)(basicPrice*material.priceMultiplier*quality);
    }



    public LinkedList<ItemAbility> getAbilities() {
        return abilities;
    }

    public String getDescription() {
        return description;
    }

    public abstract int compareToItemOfSameType(Item item);


    @Override
    public int compareTo(Item item) {
        // compare based on type, if it is different
        if(item.getClass() == this.getClass()) {
            int result = this.compareToItemOfSameType(item);
            if ( result == 0 ) {
                // If the weapon types are the same, continue in name comparison
                return this.name.compareTo(item.getName());
            }
            return result;
        }
        return this.getOrdinal() - item.getOrdinal();
    }



}
