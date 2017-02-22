package nederlands;

import monads.HList;
import nederlands.zinsdelen.Zin;
import util.MLString;

/**
 * Created by Toon Baeyens
 */
public class Zinsontleding {

    public static void main(String[] args) {
        ontleed("de poes is braaf");
        ontleed("het poesje krijgt een kusje");
        ontleed("krijgt de poes kussen");
        ontleed("poezen krijgt melk");
        ontleed("het meisjes geeft me de kusje");
        ontleed("wij geven het aan haar");
        ontleed("wij geven haar het");
        ontleed("ik geef een kus aan het meisje");
        ontleed("jij geeft een meisje een kus");
    }

    public static void ontleed(String zin) {
        System.out.println(zin);
        HList<Zin> ontleding = Zin.parse(zin);
        int minWarnings = ontleding.foldr(t -> m -> (Integer) Math.min(t.getWarnings().size(), m), Integer.MAX_VALUE);
        ontleding = ontleding.filter(t -> t.getWarnings().size() <= minWarnings);
        int mogelijkheden = ontleding.size();
        System.out.println(mogelijkheden == 0 ? "GEEN opties" : mogelijkheden == 1 ? "1 optie" : mogelijkheden + " opties");
        for (Zin o : ontleding) {
            System.out.println(new MLString("      ").concat(o.toMLString()));
            System.out.println("");
        }
        System.out.println("\n");
    }
}
