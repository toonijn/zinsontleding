package nederlands.zinsdelen;

import nederlands.woorden.*;
import parser.Parser;
import util.MLString;

import static parser.Parser.*;

/**
 * Created by Toon Baeyens
 */
public class Ding implements Zinsdeel, Congruentie {
    // Lidwoorden controleren (het poes, de hondje)
    public static final Parser<Ding> PARSER = Parser.plus(
            Nederlands.LIDWOORD
                    .bind(lw -> Nederlands.SUBSTANTIEF_TELBAAR.bind(s -> controleerLidwoord(lw, s))),
            Nederlands.LIDWOORD_BEPAALD
                    .bind(lw -> Nederlands.SUBSTANTIEF_ONTELBAAR.bind(s -> controleerLidwoord(lw, s))),
            Nederlands.SUBSTANTIEF_TELBAAR.bind(v -> guard(v.voudigheid.isMeervoud()).map(s -> new Ding(null, v))),
            Nederlands.SUBSTANTIEF_ONTELBAAR.map(s -> new Ding(null, s)));
    private final boolean bepaald;
    private final Substantief.Verbuiging verbuiging;

    private Ding(Lidwoord lidwoord, Substantief.Verbuiging verbuiging) {
        this.bepaald = lidwoord != null && lidwoord.isBepaald();
        this.verbuiging = verbuiging;
    }

    private static Parser<Ding> controleerLidwoord(Lidwoord lw, Substantief.Verbuiging v) {
        return warning(!lw.matchesWith(v), "Het lw en het znw zijn niet congruent: '" + lw.woord + "' <-> '" + v.toString() + "'")
                .bind(s -> pure(new Ding(lw, v)));
    }

    @Override
    public Voudigheid getVoudigheid() {
        return verbuiging.voudigheid;
    }

    @Override
    public Persoon getPersoon() {
        return Persoon.DERDE;
    }

    @Override
    public Geslacht getGeslacht() {
        return verbuiging.substantief.geslacht;
    }

    @Override
    public String toString() {
        return "{Ding " + (bepaald ? "bepaald " : "") + verbuiging + "}";
    }

    @Override
    public MLString toMLString() {
        return verbuiging.toMLString().addLine("Ding" + (bepaald ? " bep." : ""));
    }
}
