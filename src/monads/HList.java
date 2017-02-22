package monads;

import java.util.Iterator;

/**
 * Created by Toon Baeyens
 */
public class HList<V> implements Iterable<V>, Functor<V> {
    private final V head;
    private final HList<V> tail;

    public HList(V head) {
        this.head = head;
        this.tail = new HList<V>();
    }

    public HList(V head, HList<V> tail) {
        if (tail == null)
            throw new IllegalArgumentException();

        this.head = head;
        this.tail = tail;
    }

    public HList() {
        head = null;
        tail = null;
    }

    public static <V> HList<V> empty() {
        return new HList<>();
    }

    public static <V> HList<V> singleton(V v) {
        return new HList<>(v);
    }

    public static <V> HList<V> fromArray(V... vs) {
        HList<V> l = empty();
        for (int i = vs.length - 1; i >= 0; --i)
            l = l.prepend(vs[i]);
        return l;
    }

    public HList<V> add(HList<? extends V> v) {
        if (tail == null)
            return (HList<V>) v;
        return tail.add(v).prepend(head);
    }

    public HList<V> prepend(V head) {
        return new HList<>(head, this);
    }

    public V head() {
        return head;
    }

    public HList<V> tail() {
        return tail;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            HList<V> list = HList.this;

            @Override
            public boolean hasNext() {
                return !list.isEmpty();
            }

            @Override
            public V next() {
                V h = list.head();
                list = list.tail;
                return h;
            }
        };
    }

    @Override
    public <B> HList<B> map(Function<V, B> f) {
        if (tail == null)
            return empty();
        return new HList<>(f.get(head), tail.map(f));
    }

    public HList<V> filter(Function<V, Boolean> f) {
        return foldr(v -> r -> f.get(v) ? r.prepend(v) : r, empty());
    }

    public <B> B foldr(Function<V, Function<B, B>> f, B z) {
        if (tail == null)
            return z;
        return f.get(head).get(tail.foldr(f, z));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        boolean first = true;
        for (V v : this) {
            if (!first)
                s.append(", ");
            else
                first = false;
            s.append(v);
        }
        s.append("]");
        return s.toString();
    }

    public int size() {
        if (tail == null)
            return 0;
        return 1 + tail.size();
    }
}
