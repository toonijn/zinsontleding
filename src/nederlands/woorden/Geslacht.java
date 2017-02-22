package nederlands.woorden;

/**
 * Created by Toon Baeyens
 */
public enum Geslacht {
    M, V, O;

    public static boolean congruent(Geslacht a, Geslacht b) {
        return a == b || a == null || b == null;
    }
}
