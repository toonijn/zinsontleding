package monads;

/**
 * Created by Toon Baeyens
 */
public interface Functor<A> {
    <B> Functor<B> map(Function<A, B> f);
}
