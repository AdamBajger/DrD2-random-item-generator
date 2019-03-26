package drd2.rig.items;


import drd2.rig.Material;

import static drd2.rig.Material.*;

/**
 * Created by Admin on 30.06.2017.
 */
public enum WeaponType implements java.io.Serializable{
    SWORD, DAGGER, AXE, HAMMER, EASY, RANGED;

    private Material[] usualMaterials;

    static {
        SWORD.usualMaterials = new Material[]{BRONZE, IRON, STEEL};
        DAGGER.usualMaterials = new Material[]{BRONZE, IRON, STEEL, BONE, OBSIDIAN};
        AXE.usualMaterials = new Material[]{BRONZE, IRON, STEEL, BONE};
        HAMMER.usualMaterials = new Material[]{BRONZE, IRON, STEEL, BONE};
        EASY.usualMaterials = new Material[]{BRONZE, IRON, STEEL, WOOD};
        RANGED.usualMaterials = new Material[]{BRONZE, IRON, STEEL, BONE, WOOD};

    }

    public Material[] getUsualMaterials() {
        return usualMaterials;
    }
}
