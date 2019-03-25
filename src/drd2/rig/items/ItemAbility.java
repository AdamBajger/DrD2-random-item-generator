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

    protected AbilityType abilityType;
    protected byte level = 0; // this represents the power of the ability
    protected int price = 0; // how much this ability adds to the Weapon cost
    protected String circumstances = ""; // When the ability applies, under which circumstances, how much it costs
    protected String lore = ""; // some special abilities has its own lore and story
    protected float rarity = 0; // total rarity of this ability



    /**
     * Constructs new ItemAbility object to be inserted to an item.
     * @param abilityType Type of ability being created {@link AbilityType}
     * @param circumstances Specifies when the ability/bonus applies and what it costs eventually or what do you need to activate the ability.
     * @param level Holds the level of ability (how much you add to your roll, what id the discount for exhaustion, etc.)
     * @param price Price of this ability. It is added to the total item cost.
     * @param rarity How rare is this ability, how powerful it is. The higher value, the more rare.
     *
     */

    ItemAbility(AbilityType abilityType, String circumstances, byte level, int price, float rarity, ResourceTypes resourceType) {
        this.abilityType = abilityType;
        this.circumstances = circumstances;
        this.level = level;
        this.price = price;
        this.rarity = rarity;
        this.resourceType = resourceType;





    }

    public String getDescription() {
        if(this.level == 0) {
            return "Neposkytuje žádný bonus";
        }
        String abilityDescription = "";
        switch(this.abilityType) {
            case EXHAUSTION:
                abilityDescription = abilityDescription + "poskytuje slevu " + this.level + " na vyčerpání při " + this.circumstances;
                break;
            case BONUS:
                abilityDescription = abilityDescription + "poskytuje bonus +" + this.level + " k hodu kostkou při " + this.circumstances;

                break;
            case ACTIVATE:
                abilityDescription = abilityDescription + "umožňuje " + this.circumstances + ". Aktivace stojí " + this.level;
                abilityDescription += TXTGen.activationCosts(level, resourceType);

                break;
            case SPECIAL:
                abilityDescription = abilityDescription + "Vlastnost: " + this.circumstances + " Úroveň: " + this.level + " Rarita: " + this.rarity;
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
        return price;
    }

    public String getCircumstances() {
        return circumstances;
    }

    public String getLore() {
        return lore;
    }

    public float getRarity() {
        return rarity;
    }
}
