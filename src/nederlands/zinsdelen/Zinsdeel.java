package nederlands.zinsdelen;

import util.MLString;

/**
 * Created by Toon Baeyens
 */
public interface Zinsdeel {
    default MLString toMLString() {
        return new MLString();
    }
}
