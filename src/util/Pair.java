package util;

/**
 * Created by Toon Baeyens
 */
public class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return new Pair<>(a, b);
    }

    public A first() {
        return a;
    }

    public B second() {
        return b;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}
