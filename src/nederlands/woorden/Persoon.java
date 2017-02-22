package nederlands.woorden;

/**
 * Created by Toon Baeyens
 */
public enum Persoon {
    EERSTE(1), TWEEDE(2), DERDE(3);

    private static Persoon[] get = {null, EERSTE, TWEEDE, DERDE};
    private int value;

    Persoon(int value) {
        this.value = value;
    }

    public static boolean congruent(Persoon a, Persoon b) {
        return a == b || a == null || b == null;
    }

    public static Persoon get(int a) {
        return get[a];
    }

    public int get() {
        return value;
    }

    @Override
    public String toString() {
        return value + (value == 1 ? "ste" : "de");
    }
}
