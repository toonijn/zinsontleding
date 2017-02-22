package monads;

/**
 * Created by Toon Baeyens
 */
public interface Monad<A> extends Functor<A> {
    <B> Monad<B> bind(Function<A, ? extends Monad<B>> f);

    default <B> Monad<B> set(Monad<B> mb) {
        return bind(a -> mb);
    }
}
