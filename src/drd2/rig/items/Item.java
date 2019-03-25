package drd2.rig.items;

import drd2.rig.Material;

import java.util.LinkedList;

public class Item {
    public  String name;
    byte quality;
    Material material;
    int price;
    float rarity;
    LinkedList<ItemAbility> abilities;
    String description;

    public Item(String name,
                byte quality,
                Material material,
                int price,
                float rarity,
                LinkedList<ItemAbility> abilities,
                String description) {
        this.name = name;
        this.quality = quality;
        this.material = material;
        this.price = price;
        this.rarity = rarity;
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
        return price;
    }

    public float getRarity() {
        return rarity;
    }

    public LinkedList<ItemAbility> getAbilities() {
        return abilities;
    }

    public String getDescription() {
        return description;
    }

}
