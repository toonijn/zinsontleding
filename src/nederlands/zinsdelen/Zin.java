package nederlands.zinsdelen;

import monads.HList;
import nederlands.woorden.Congruentie;
import nederlands.woorden.Valentie;
import parser.Parser;
import parser.Warnings;
import util.MLString;

import static parser.Parser.*;

/**
 * Created by Toon Baeyens
 */
public class Zin implements Zinsdeel {
    private final static Parser<Zin> PARSER = Parser.plus(
            Onderwerp.PARSER.bind(ond ->
                    Persoonsvorm.PARSER.bind(
                            pv -> warning(!Congruentie.congruent(ond, pv), "Het onderwerp en de pv. zijn niet congruent")
                                    .set(parseVoorwerpen(ond, pv).map(vwn -> new Zin(false, ond, pv, vwn))))),
            Persoonsvorm.PARSER.bind(pv ->
                    Onderwerp.PARSER.bind(
                            ond -> warning(!Congruentie.congruent(ond, pv), "Het onderwerp en de pv. zijn niet congruent")
                                    .set(parseVoorwerpen(ond, pv).map(vwn -> new Zin(true, ond, pv, vwn))))));
    private final Onderwerp onderwerp;
    private final Persoonsvorm persoonsvorm;
    private final Voorwerpen voorwerpen;
    private final boolean vraag;
    private String origineel = null;
    private Warnings warnings = null;

    private Zin(boolean vraag, Onderwerp ond, Persoonsvorm pv, Voorwerpen vwn) {
        this.vraag = vraag;
        onderwerp = ond;
        persoonsvorm = pv;
        voorwerpen = vwn;
    }

    public static HList<Zin> parse(String zin) {
        return PARSER.bind(z -> REST.bind(r -> {
            if (r.isEmpty())
                return pure(z);
            String rest = r.tail().foldr(s -> a -> a + ' ' + s, r.head());
            return warning(!r.isEmpty(), "'" + rest + "' is niet verwerkt.").set(pure(z));
        })).get(HList.fromArray(zin.split("\\s+")))
                .map(t -> {
                    Zin z = t.first();
                    z.setOrigineel(zin);
                    z.setWarnings(t.second());
                    return z;
                });
    }

    private static Parser<Voorwerpen> parseVoorwerpen(Onderwerp ond, Persoonsvorm pv) {
        Parser<Voorwerpen> voorwerpen = LijdendVoorwerp.PARSER.map(lv -> new Voorwerpen(lv, null));
        String voorzetsel = pv.werkwoord.meewerkendVoorwerp;
        if (voorzetsel != null)
            voorwerpen = voorwerpen.add(
                    MeewerkendVoorwerp.PARSER.bind(mv -> LijdendVoorwerp.PARSER.map(lv -> new Voorwerpen(lv, mv))))
                    .add(LijdendVoorwerp.PARSER.bind(lv -> match(voorzetsel).set(MeewerkendVoorwerp.PARSER.map(mv -> new Voorwerpen(lv, mv)))));

        return Parser.plus(
                guard(pv.werkwoord.valentie != Valentie.OVERGANKELIJK).set(pure(null)),
                guard(pv.werkwoord.valentie != Valentie.ONOVERGANKELIJK).set(voorwerpen));
    }

    @Override
    public String toString() {
        return "{" + (vraag ? "Vraag" : "Zin") + " " +
                onderwerp + " " +
                persoonsvorm + (voorwerpen.available() ? " " + voorwerpen : "") + "}";
    }

    @Override
    public MLString toMLString() {
        MLString s = onderwerp.toMLString().concat(persoonsvorm.toMLString());
        if (voorwerpen != null && voorwerpen.available())
            s = s.concat(voorwerpen.toMLString());
        s = s.addLine().add(vraag ? "Vraag" : "Zin");
        if (warnings != null)
            s = s.concat("     ").concat(warnings.toMLString());
        return s;
    }

    public String getOrigineel() {
        return origineel;
    }

    private void setOrigineel(String origineel) {
        this.origineel = origineel;
    }

    public Warnings getWarnings() {
        return warnings;
    }

    private void setWarnings(Warnings warnings) {
        this.warnings = warnings;
    }

    public static class Voorwerpen {
        public final LijdendVoorwerp lijdend;
        public final MeewerkendVoorwerp meewerkend;

        public Voorwerpen(LijdendVoorwerp lijdend, MeewerkendVoorwerp meewerkend) {
            this.lijdend = lijdend;
            this.meewerkend = meewerkend;
        }

        public MLString toMLString() {
            if (meewerkend == null && lijdend == null)
                return new MLString();
            if (meewerkend == null)
                return lijdend.toMLString();
            else if (lijdend == null)
                return meewerkend.toMLString();
            else
                return lijdend.toMLString().concat(meewerkend.toMLString()).addLine("Voorwerpen");
        }

        public boolean available() {
            return lijdend != null || meewerkend != null;
        }
    }
}
