package drd2.rig.items;

import drd2.rig.CzechAlphabetEnum;
import drd2.rig.Material;

import java.util.LinkedList;

/**
 * Created by Admin on 30.06.2017.
 */
public class Weapon extends Item implements java.io.Serializable {

    private WeaponType weaponType;
    private Hands hands;




    public Weapon(String name, WeaponType weaponType, Hands hands,
            byte quality, Material material, int price, float rarity,
            LinkedList<ItemAbility> abilities,
            String description
    ) {
        super(name, quality, material, price, rarity, abilities, description);

        this.weaponType = weaponType;
        this.hands = hands;
    }


    public WeaponType getWeaponType() {
        return weaponType;
    }


    public Hands getHands() {
        return hands;
    }

    @Override
    public int getOrdinal() {
        return 2;
    }

    @Override
    public int compareToItemOfSameType(Item item) {
        // compare based on type, if it is different
        if(item instanceof Weapon) {
            if(((Weapon)item).weaponType != this.weaponType) {
                return this.weaponType.ordinal() - ((Weapon)item).weaponType.ordinal();
            }
            return this.hands.ordinal() - ((Weapon)item).getHands().ordinal();

        }

        return 0;
    }
}
