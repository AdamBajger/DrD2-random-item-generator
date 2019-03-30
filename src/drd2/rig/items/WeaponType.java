package drd2.rig.items;


import drd2.rig.Material;
import drd2.rig.generators.BagOfStuff;

import static drd2.rig.Material.*;

/**
 * Created by Admin on 30.06.2017.
 */
public enum WeaponType implements java.io.Serializable{
    SWORD, CURVED, DAGGER, AXE, HAMMER, EASY, RANGED;

    private BagOfStuff<Material> usualMaterials;

    static {
        SWORD.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL});
        CURVED.usualMaterials = new BagOfStuff<>(new Material[]{IRON, STEEL});
        DAGGER.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL, BONE, OBSIDIAN});
        AXE.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL, BONE});
        HAMMER.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL, BONE});
        EASY.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL, WOOD});
        RANGED.usualMaterials = new BagOfStuff<>(new Material[]{BRONZE, IRON, STEEL, BONE, WOOD});

    }

    public BagOfStuff<Material> getUsualMaterials() {
        return usualMaterials;
    }

}
