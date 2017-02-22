package util;

/**
 * Created by Toon Baeyens
 */
public class Triplet<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static <A, B, C> Triplet<A, B, C> triplet(A a, B b, C c) {
        return new Triplet<>(a, b, c);
    }

    public A first() {
        return a;
    }

    public Triplet<A, B, C> setFirst(A a) {
        return new Triplet<>(a, b, c);
    }

    public B second() {
        return b;
    }

    public Triplet<A, B, C> setSecond(B b) {
        return new Triplet<>(a, b, c);
    }

    public C third() {
        return c;
    }

    public Triplet<A, B, C> setThird(C c) {
        return new Triplet<>(a, b, c);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ", " + c + ")";
    }
}
