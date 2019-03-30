package drd2.rig.items;

import drd2.rig.Material;

import java.util.LinkedList;

/**
 * This class serves as a template for generating final items
 * It contains information about item that is definite
 * It randomly fills in the remaining information based on item definitions
 */
public class ItemBuilder {
    private String name;
    private int basicPrice;
    private LinkedList<ItemAbility> abilities;
    private String description;

    public ItemBuilder(String name, int basicPrice, LinkedList<ItemAbility> abilities, String description) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.abilities = abilities;
        this.description = description;
    }
}
