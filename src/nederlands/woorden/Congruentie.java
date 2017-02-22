package nederlands.woorden;

/**
 * Created by Toon Baeyens
 */
public interface Congruentie {
    static boolean congruent(Congruentie a, Congruentie b) {
        return !(a == null || b == null)
                && Geslacht.congruent(a.getGeslacht(), b.getGeslacht())
                && Voudigheid.congruent(a.getVoudigheid(), b.getVoudigheid())
                && Persoon.congruent(a.getPersoon(), b.getPersoon());
    }

    default Voudigheid getVoudigheid() {
        return null;
    }

    default Persoon getPersoon() {
        return null;
    }

    default Geslacht getGeslacht() {
        return null;
    }
}
