package nederlands.woorden;

import parser.Parser;
import util.MLString;

import static nederlands.woorden.Geslacht.O;

/**
 * Created by Toon Baeyens
 */
public enum Lidwoord {
    EEN("een", false) {
        @Override
        public boolean matchesWith(Substantief.Verbuiging v) {
            return v.voudigheid.isEnkelvoud();
        }
    }, DE("de", true) {
        @Override
        public boolean matchesWith(Substantief.Verbuiging v) {
            return (v.getGeslacht() != O && !v.verkleinwoord) || v.voudigheid.isMeervoud();
        }
    }, HET("het", true) {
        @Override
        public boolean matchesWith(Substantief.Verbuiging v) {
            return (v.getGeslacht() == O || v.verkleinwoord) && v.voudigheid.isEnkelvoud();
        }
    };

    public final String woord;
    private final boolean bepaald;

    Lidwoord(String woord, boolean bepaald) {
        this.woord = woord;
        this.bepaald = bepaald;
    }

    public abstract boolean matchesWith(Substantief.Verbuiging v);

    public Parser<Lidwoord> getParser() {
        return Parser.match(woord).map(w -> this);
    }

    @Override
    public String toString() {
        return "{Lidwoord " + woord + "}";
    }

    public MLString toMLString() {
        return new MLString(woord);
    }

    public boolean isBepaald() {
        return bepaald;
    }
}
