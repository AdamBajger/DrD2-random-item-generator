package damn.fuck.shit;



/**
 * Created by Admin on 30.06.2017.
 */
public enum WeaponType implements java.io.Serializable{
    SWORD, DAGGER, AXE, HAMMER, EASY, RANGED;

    private Material[] usualMaterials;

    static {
        SWORD.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL};
        DAGGER.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL, Material.BONE, Material.OBSIDIAN};
        AXE.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL, Material.BONE};
        HAMMER.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL, Material.BONE};
        EASY.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL};
        RANGED.usualMaterials = new Material[]{Material.BRONZE, Material.IRON, Material.STEEL, Material.BONE};

    }

    public Material[] getUsualMaterials() {
        return usualMaterials;
    }
}
