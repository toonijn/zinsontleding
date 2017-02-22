package nederlands.woorden;

import nederlands.zinsdelen.Zinsdeel;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class Substantief extends Woord<Substantief.Verbuiging> {
    public final Geslacht geslacht;
    private final boolean telbaar;
    private final String verkleinwoord;
    private final String meervoud;

    public Substantief(String woord, Geslacht geslacht) {
        this(woord, geslacht, true);
    }

    public Substantief(String woord, Geslacht geslacht, boolean telbaar) {
        super(woord);
        this.geslacht = geslacht;
        this.telbaar = telbaar;
        this.meervoud = woord + "en";
        this.verkleinwoord = woord + "je";
    }

    public Substantief(String woord, Geslacht geslacht, String meervoud, String verkleinwoord) {
        super(woord);
        this.geslacht = geslacht;
        this.telbaar = true;
        this.meervoud = meervoud;
        this.verkleinwoord = verkleinwoord;
    }

    @Override
    public Parser<Verbuiging> getParser() {
        Parser<Verbuiging> parser = Parser.match(woord).map(s ->
                new Verbuiging(Substantief.this, false, false));
        if (!telbaar)
            return parser;
        if (meervoud != null)
            parser = parser.add(Parser.match(meervoud).map(s -> new Verbuiging(Substantief.this, true, false)));
        if (verkleinwoord != null)
            parser = parser.add(Parser.match(verkleinwoord).map(s -> new Verbuiging(Substantief.this, false, true)))
                    .add(Parser.match(verkleinwoord + 's').map(s -> new Verbuiging(Substantief.this, false, true)));
        return parser;
    }

    public String verbuig(boolean meervoud, boolean verkleinwoord) {
        if (verkleinwoord)
            return this.verkleinwoord + (meervoud ? 's' : "");
        if (meervoud)
            return this.meervoud;
        return woord;
    }

    public boolean isTelbaar() {
        return telbaar;
    }

    public static class Verbuiging implements Zinsdeel, Congruentie {
        public final Substantief substantief;
        public final Voudigheid voudigheid;
        public final boolean verkleinwoord;

        public Verbuiging(Substantief substantief, boolean meervoud, boolean verkleinwoord) {
            this.substantief = substantief;
            this.voudigheid = meervoud ? Voudigheid.MEER : Voudigheid.ENKEL;
            this.verkleinwoord = verkleinwoord;
        }

        @Override
        public Voudigheid getVoudigheid() {
            return voudigheid;
        }

        @Override
        public Persoon getPersoon() {
            return Persoon.DERDE;
        }

        @Override
        public Geslacht getGeslacht() {
            return substantief.geslacht;
        }

        @Override
        public String toString() {
            return substantief.verbuig(voudigheid.isMeervoud(), verkleinwoord);
        }

        @Override
        public MLString toMLString() {
            return new MLString(substantief.verbuig(voudigheid.isMeervoud(), verkleinwoord));
        }
    }
}
