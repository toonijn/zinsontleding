package nederlands.zinsdelen;

import nederlands.woorden.Nederlands;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class LijdendVoorwerp implements Zinsdeel {
    public static final Parser<LijdendVoorwerp> PARSER = Parser.plus(
            Nederlands.PERSOONLIJKE_VOORWERPSVORM.map(WrapperLijdendVoorwerp::new),
            Ding.PARSER.map(WrapperLijdendVoorwerp::new)
    );

    private static class WrapperLijdendVoorwerp<D extends Zinsdeel> extends LijdendVoorwerp {
        private final D content;

        private WrapperLijdendVoorwerp(D content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "{Lijdend voorwerp " + content + "}";
        }

        @Override
        public MLString toMLString() {
            return content.toMLString().addLine("Lijdend voorwerp");
        }
    }
}
