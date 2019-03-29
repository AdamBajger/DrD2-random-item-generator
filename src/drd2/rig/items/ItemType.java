package drd2.rig.items;

/**
 * Created by Admin on 11.07.2017.
 */
public enum ItemType {
    WEAPON, TOOL, ARMOR, FOOD, SPECIAL, JEWELRY;

    public static final String text(ItemType it) {
        switch (it) {
            case WEAPON:
                return "weapon";
            case TOOL:
                return "tool";
            case ARMOR:
                return "armor";
            case FOOD:
                return "foor";
            case SPECIAL:
                return "special";
            case JEWELRY:
                return "jewelry";
        }
        throw new RuntimeException("Nonimplemented functionality for ItemType.text("+ it +")");
    }


}