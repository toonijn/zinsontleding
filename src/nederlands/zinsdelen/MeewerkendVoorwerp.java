package nederlands.zinsdelen;

import nederlands.woorden.Nederlands;
import parser.Parser;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class MeewerkendVoorwerp implements Zinsdeel {
    public static final Parser<MeewerkendVoorwerp> PARSER = Parser.plus(
            Nederlands.PERSOONLIJKE_VOORWERPSVORM.map(WrapperMeewerkendVoorwerp::new),
            Ding.PARSER.map(WrapperMeewerkendVoorwerp::new)
    );

    private static class WrapperMeewerkendVoorwerp<D extends Zinsdeel> extends MeewerkendVoorwerp {
        private final D content;

        private WrapperMeewerkendVoorwerp(D content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "{Meewerkend voorwerp " + content + "}";
        }

        @Override
        public MLString toMLString() {
            return content.toMLString().addLine("Meewerkend voorwerp");
        }
    }
}
