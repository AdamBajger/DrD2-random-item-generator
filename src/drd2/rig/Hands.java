package drd2.rig;

/**
 * Created by Admin on 10.07.2017.
 */
public enum Hands {
    NO_HANDS, ONE_HANDED, BOTH_HANDED;

    public static final Hands[] array;

    static {
        array = Hands.values();
    }

}
