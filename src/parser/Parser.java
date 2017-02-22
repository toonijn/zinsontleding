package parser;

import monads.Function;
import monads.HList;
import monads.Monad;
import util.Triplet;

import static monads.HList.empty;
import static monads.HList.singleton;
import static util.Triplet.triplet;

/**
 * Created by Toon Baeyens
 */
public interface Parser<A> extends Monad<A>, Function<HList<String>, HList<Triplet<A, Warnings, HList<String>>>> {
    Parser<Void> EMPTY = s -> empty();
    Parser<Void> SINGLE = pure(null);
    Parser<String> WORD = s -> s.isEmpty() ? empty() : singleton(triplet(s.head(), Warnings.EMPTY, s.tail()));
    Parser<HList<String>> REST = s -> singleton(triplet(s, Warnings.EMPTY, empty()));

    static <A> Parser<A> pure(A a) {
        return s -> singleton(triplet(a, Warnings.EMPTY, s));
    }

    static <A> Parser<Void> guard(boolean t) {
        return t ? SINGLE : EMPTY;
    }

    static <A> Parser<Void> warning(boolean t, String warning) {
        return s -> singleton(triplet(null, t ? new Warnings(warning) : Warnings.EMPTY, s));
    }

    static Parser<String> match(String s) {
        return WORD.bind(
                t -> guard(s.equals(t)).set(pure(t)));
    }

    static Parser<String> containedIn(Iterable<String> strings) {
        Parser<String> parser = null;
        for (String s : strings)
            parser = parser == null ? match(s) : parser.add(match(s));
        return parser;
    }

    static <A> Parser<A> plus(Iterable<Parser<A>> parsers) {
        Parser<A> parser = s -> empty();
        for (Parser<A> p : parsers)
            parser = parser.add(p);
        return parser;
    }

    static <A> Parser<A> plus(Parser<? extends A>... parsers) {
        Parser<A> parser = s -> empty();
        for (Parser<? extends A> p : parsers)
            parser = parser.add((Parser<A>) p);
        return parser;
    }

    default Parser<A> add(Parser<A> m) {
        return s -> get(s).add(m.get(s));
    }

    @Override
    default <B> Parser<B> map(Function<A, B> f) {
        return s ->
                get(s).map(taws ->
                        triplet(f.get(taws.first()), taws.second(), taws.third()));
    }

    @Override
    default <B> Parser<B> bind(Function<A, ? extends Monad<B>> f) {
        return s -> {
            HList<Triplet<A, Warnings, HList<String>>> l1 = get(s);
            HList<Triplet<B, Warnings, HList<String>>> result = empty();
            for (Triplet<A, Warnings, HList<String>> t1 : l1) {
                Parser<B> mb = (Parser<B>) f.get(t1.first());
                for (Triplet<B, Warnings, HList<String>> t2 : mb.get(t1.third())) {
                    result = result.prepend(t2.setSecond(t2.second().add(t1.second())));
                }
            }
            return result;
        };
    }

    @Override
    default <B> Parser<B> set(Monad<B> mb) {
        return bind(a -> mb);
    }
}
