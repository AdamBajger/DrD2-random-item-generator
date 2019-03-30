package drd2.rig.items;

import drd2.rig.Material;
import drd2.rig.generators.BagOfStuff;

import java.util.LinkedList;

public class WeaponBuilder {
    private String name;
    private int basicPrice;
    private LinkedList<ItemAbility> abilities;
    private String description;
    private Hands hands;
    private WeaponType weaponType;
    private BagOfStuff<Material> bagOfMaterials;

    public WeaponBuilder(String name, int basicPrice, LinkedList<ItemAbility> abilities, String description,
                         Hands hands, WeaponType weaponType) {
        this( name, basicPrice, abilities,  description, hands, weaponType, weaponType.getUsualMaterials());
    }

    public WeaponBuilder(String name, int basicPrice, LinkedList<ItemAbility> abilities, String description,
                         Hands hands, WeaponType weaponType, BagOfStuff<Material> bagOfMaterials) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.abilities = abilities;
        this.description = description;
        this.hands = hands;
        this.weaponType = weaponType;
        this.bagOfMaterials = bagOfMaterials;
    }
}
