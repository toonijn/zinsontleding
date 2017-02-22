package nederlands.woorden;

import nederlands.zinsdelen.Zinsdeel;
import parser.Parser;
import util.MLString;

public class Werkwoord extends Woord<Werkwoord.Vervoeging> {
    public final Valentie valentie;
    public final String meewerkendVoorwerp;
    private final String stam;
    private final String infinitief;
    private final boolean koppelwerkwoord;
    private final String[] uitzondering;


    public Werkwoord(String stam, String infinitief, Valentie valentie) {
        this(stam, infinitief, valentie, null, null);
    }

    public Werkwoord(String stam, String infinitief, Valentie valentie, String meewerkendVoorwerp) {
        this(stam, infinitief, valentie, meewerkendVoorwerp, null);
    }

    public Werkwoord(String stam, String infinitief, Valentie valentie, String meewerkendVoorwerp, String[] uitzondering) {
        super(infinitief);
        this.stam = stam;
        this.infinitief = infinitief;
        this.valentie = valentie;
        this.uitzondering = uitzondering;
        this.meewerkendVoorwerp = meewerkendVoorwerp;
        koppelwerkwoord = Nederlands.koppelwerkwoorden.contains(infinitief);
    }

    public Vervoeging werkwoord(int persoon, boolean meervoud) {
        return new Vervoeging(this, persoon, meervoud);
    }

    public String vervoeg(int persoon, boolean meervoud) {
        int i = persoon + (meervoud ? 2 : -1);
        if (uitzondering == null || uitzondering.length <= i) {
            char lastChar = stam.charAt(stam.length() - 1);
            return meervoud ? infinitief : (persoon == 1 ? stam : stam + (lastChar != 't' ? 't' : ""));
        }
        return uitzondering[i];
    }

    @Override
    public Parser<Vervoeging> getParser() {
        return Parser.plus(
                Parser.match(vervoeg(1, false)).map(s -> werkwoord(1, false)),
                Parser.match(vervoeg(2, false)).map(s -> werkwoord(2, false)),
                Parser.match(vervoeg(3, false)).map(s -> werkwoord(3, false)),
                Parser.match(vervoeg(1, true)).map(s -> werkwoord(1, true)),
                Parser.match(vervoeg(2, true)).map(s -> werkwoord(2, true)),
                Parser.match(vervoeg(3, true)).map(s -> werkwoord(3, true)));
    }

    public boolean isKoppelwerkwoord() {
        return koppelwerkwoord;
    }

    public static class Vervoeging implements Zinsdeel {
        public final Werkwoord werkwoord;
        public final Persoon persoon;
        public final Voudigheid voudigheid;

        public Vervoeging(Werkwoord werkwoord, int persoon, boolean meervoud) {
            this.werkwoord = werkwoord;
            this.persoon = Persoon.get(persoon);
            this.voudigheid = meervoud ? Voudigheid.MEER : Voudigheid.ENKEL;
        }

        @Override
        public String toString() {
            return "{Vervoeging " + persoon + (voudigheid.isMeervoud() ? " mv" : "") + " " + werkwoord + "}";
        }

        @Override
        public MLString toMLString() {
            return new MLString(werkwoord.vervoeg(persoon.get(), voudigheid.isMeervoud()));
        }
    }
}