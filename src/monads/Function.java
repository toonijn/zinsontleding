package monads;

/**
 * Created by Toon Baeyens
 */
public interface Function<A, B> {
    B get(A a);

    default <C> Function<C, B> get(Function<C, A> f) {
        return c -> this.get(f.get(c));
    }
}
