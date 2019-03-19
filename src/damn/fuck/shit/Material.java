package damn.fuck.shit;

/**
 * Created by Admin on 11.07.2017.
 */
public enum Material implements java.io.Serializable {
    IRON, BRONZE, STEEL, WOOD, BONE, OBSIDIAN;

    public float priceMultiplier;

    static {
        IRON.priceMultiplier = (float)1;
        BRONZE.priceMultiplier = (float)0.8;
        STEEL.priceMultiplier = (float)1.3;
        WOOD.priceMultiplier = (float)0.1;
        BONE.priceMultiplier = (float)1.1;
        OBSIDIAN.priceMultiplier = (float)1.4;
    }

    public float rarityMultiplier;

    static {
        IRON.rarityMultiplier = (float)1;
        BRONZE.rarityMultiplier = (float)0.8;
        STEEL.rarityMultiplier = (float)1.1;
        WOOD.rarityMultiplier = (float)0.5;
        BONE.rarityMultiplier = (float)1.5;
        OBSIDIAN.rarityMultiplier = (float)2;
    }
}
