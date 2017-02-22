package nederlands.woorden;

/**
 * Created by Toon Baeyens
 */
public class Voornaamwoord extends Woord<Voornaamwoord> implements Congruentie {
    private final Persoon persoon;
    private final Voudigheid voudigheid;
    private final Geslacht geslacht;


    public Voornaamwoord(String woord, int persoon, boolean meervoud) {
        this(woord, persoon, meervoud, null);
    }

    public Voornaamwoord(String woord, int persoon, boolean meervoud, Geslacht geslacht) {
        super(woord);
        this.persoon = Persoon.get(persoon);
        this.voudigheid = meervoud ? Voudigheid.MEER : Voudigheid.ENKEL;
        this.geslacht = geslacht;
    }

    @Override
    public Voudigheid getVoudigheid() {
        return voudigheid;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public Geslacht getGeslacht() {
        return geslacht;
    }
}
