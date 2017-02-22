package nederlands.zinsdelen;

import nederlands.woorden.*;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class Persoonsvorm implements Zinsdeel, Congruentie {

    public static Parser<Persoonsvorm> PARSER = Nederlands.WERKWOORD.map(Persoonsvorm::new);

    public final Werkwoord werkwoord;
    public final Werkwoord.Vervoeging vervoeging;

    public Persoonsvorm(Werkwoord.Vervoeging vervoeging) {
        this.vervoeging = vervoeging;
        werkwoord = vervoeging.werkwoord;
    }

    @Override
    public String toString() {
        return "{Persoonsvorm  " + werkwoord + "}";
    }

    @Override
    public Persoon getPersoon() {
        return vervoeging.persoon;
    }

    @Override
    public Voudigheid getVoudigheid() {
        return vervoeging.voudigheid;
    }

    @Override
    public MLString toMLString() {
        return vervoeging.toMLString().addLine("pv. " + getPersoon() + " " + getVoudigheid());
    }
}
