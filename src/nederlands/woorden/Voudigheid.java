package nederlands.woorden;

/**
 * Created by Toon Baeyens
 */
public enum Voudigheid {
    ENKEL, MEER;

    static boolean congruent(Voudigheid a, Voudigheid b) {
        return a == b || a == null || b == null;
    }

    public boolean isMeervoud() {
        return this == MEER;
    }

    public boolean isEnkelvoud() {
        return this == ENKEL;
    }

    @Override
    public String toString() {
        return this == ENKEL ? "ev." : "mv.";
    }
}
