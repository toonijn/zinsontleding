package nederlands.woorden;

import nederlands.zinsdelen.Zinsdeel;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class Woord<W> implements Zinsdeel {
    protected final String woord;

    public Woord(String woord) {
        this.woord = woord;
    }

    public Parser<W> getParser() {
        return Parser.match(woord).map(w -> (W) this);
    }

    @Override
    public String toString() {
        return "{" + getClass().getSimpleName() + " " + woord + "}";
    }

    @Override
    public MLString toMLString() {
        String name = getClass().getSimpleName();
        return new MLString(woord).addLine(name);
    }
}
