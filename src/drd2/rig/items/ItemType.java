package drd2.rig.items;

/**
 * Created by Admin on 11.07.2017.
 */
public enum ItemType {
    WEAPON, TOOL, ARMOR, FOOD, SPECIAL, JEWELRY;


    public String text;

    static {
        WEAPON.text = "weapon";
        TOOL.text = "tool";
        ARMOR.text = "armor";
        FOOD.text = "food";
        SPECIAL.text = "special";
        JEWELRY.text = "jewelry";
    }
}