package drd2.rig.items;

import drd2.rig.ResourceTypes;
import drd2.rig.generators.TXTGen;

/**
 * This class is an instance of one ability bonus placed on a weapon. It has multiple types.
 * Types:
 *  Exhaustion discount
 *  Bonus +X to a dice roll
 *  some special bonus
 *  @author Adam Václav Bajger
 *  @version 1.0.0
 */

public class ItemAbility {
    public static int MIN_RARITY_VALUE = 1;
    private final ResourceTypes resourceType;

    private AbilityType abilityType;
    private byte level; // this represents the power of the ability
    private int price; // how much this ability adds to the Weapon cost
    private String circumstances; // When the ability applies, under which circumstances, how much it costs
    private String lore; // some special abilities has its own lore and story


    /**
     * Constructs new ItemAbility object to be inserted to an item.
     * @param abilityType Type of ability being created {@link AbilityType}
     * @param level Holds the level of ability (how much you add to your roll, what id the discount for exhaustion, etc.)
     * @param price Price of this ability. It is added to the total item cost.
     * @param circumstances Specifies when the ability/bonus applies and what it costs eventually or what do you need to activate the ability.
     * @param lore Tells the story of the ability
     *
     */

    public ItemAbility(AbilityType abilityType, byte level, int price, ResourceTypes resourceType, String circumstances, String lore) {
        this.abilityType = abilityType;
        this.level = level;
        this.price = price;
        this.resourceType = resourceType;
        this.circumstances = circumstances;
        this.lore = lore;
    }

    public String getDescription() {
        String abilityDescription = "";

        switch(this.abilityType) {
            case EXHAUSTION:
                if (level != 0) {
                    abilityDescription = "Poskytuje slevu " + this.level + " na vyčerpání " + this.circumstances + ". ";
                }
                break;
            case BONUS:
                if (level != 0) {
                    abilityDescription = "Poskytuje bonus +" + this.level + " k hodu kostkou " + this.circumstances
                            + ". ";
                }

                break;
            case ACTIVATE:
                abilityDescription = "Umožňuje " + this.circumstances + ", aktivace stojí " + this.level + " "
                        + TXTGen.activationCosts(level, resourceType) + ". ";

                break;
            case SPECIAL:
                abilityDescription = abilityDescription + "Vlastnost: " + this.circumstances + " - Úroveň: "
                        + this.level + ". ";
                break;
        }
        return abilityDescription;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public byte getLevel() {
        return level;
    }

    public int getPrice() {
        return price*level;
    }

    public String getCircumstances() {
        return circumstances;
    }

    public String getLore() {
        return lore;
    }

    @Deprecated
    public float getRarity() {
        // total rarity of this ability
        float rarity = 0;
        return rarity;
    }
}
