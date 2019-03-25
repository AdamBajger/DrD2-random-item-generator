package drd2.rig.generators;

import drd2.rig.ResourceTypes;

public class TXTGen {

    public static String activationCosts(int amount, ResourceTypes type) {
        switch (type) {
            case SOUL:
                if (amount == 1) {
                    return "duši";
                } else if (amount < 5) {
                    return "duše";
                } else {
                    return "duší";
                }
            case BODY:
                if (amount == 1) {
                    return "tělo";
                } else if (amount < 5) {
                    return "těla";
                } else {
                    return "těl";
                }
            case INFLUENCE:
                if (amount == 1) {
                    return "vliv";
                } else if (amount < 5) {
                    return "vlivy";
                } else {
                    return "vlivů";
                }
            case INGREDIENTS:
                if (amount == 1) {
                    return "surovinu";
                } else if (amount < 5) {
                    return "suroviny";
                } else {
                    return "surovin";
                }
            default:
                if (amount == 1) {
                    return "zdroj";
                } else if (amount < 5) {
                    return "zdroje";
                } else {
                    return "zdrojů";
                }
        }

    }
}
