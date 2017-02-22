package nederlands.zinsdelen;

import nederlands.woorden.*;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class Onderwerp implements Zinsdeel, Congruentie {
    public static final Parser<Onderwerp> PARSER = Parser.plus(
            Nederlands.PERSOONLIJKE_VOORNAAMWOORD.map(WrapperOnderwerp::new),
            Ding.PARSER.map(WrapperOnderwerp::new)
    );

    public MLString toMLString() {
        return null;
    }

    private static class WrapperOnderwerp<D extends Congruentie & Zinsdeel> extends Onderwerp {
        private final D content;

        private WrapperOnderwerp(D content) {
            this.content = content;
        }

        @Override
        public Voudigheid getVoudigheid() {
            return content.getVoudigheid();
        }

        @Override
        public Persoon getPersoon() {
            return content.getPersoon();
        }

        @Override
        public Geslacht getGeslacht() {
            return content.getGeslacht();
        }

        @Override
        public String toString() {
            return "{Onderwerp " + content + "}";
        }

        public MLString toMLString() {
            return content.toMLString().addLine("Onderwerp");
        }
    }
}
