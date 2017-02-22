package parser;

import monads.Function;
import monads.HList;
import util.MLString;

import java.util.Iterator;

/**
 * Created by Toon Baeyens
 */
public class Warnings implements Iterable<String> {
    public static final Warnings EMPTY = new Warnings();

    private final HList<String> warnings;
    private final int size;

    public Warnings() {
        warnings = new HList<>();
        size = 0;
    }

    public Warnings(String warning) {
        warnings = new HList<>(warning);
        size = 1;
    }

    private Warnings(HList<String> warnings, int size) {
        this.warnings = warnings;
        this.size = size;
    }

    @Override
    public Iterator<String> iterator() {
        return warnings.iterator();
    }

    public <B> HList<B> map(Function<String, B> f) {
        return warnings.map(f);
    }

    public <B> B foldr(Function<String, Function<B, B>> f, B z) {
        return warnings.foldr(f, z);
    }

    public Warnings add(Warnings v) {
        return new Warnings(warnings.add(v.warnings), size + v.size());
    }

    public Warnings prepend(String head) {
        return new Warnings(warnings.prepend(head), size + 1);
    }

    public MLString toMLString() {
        MLString s = new MLString();
        for (String w : warnings) {
            s = s.add("* " + w);
        }
        return s;
    }

    @Override
    public String toString() {
        return warnings.toString();
    }

    public int size() {
        return size;
    }
}
