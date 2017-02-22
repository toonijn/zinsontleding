package nederlands.woorden;

import monads.HList;
import parser.Parser;

import java.util.HashSet;
import java.util.Set;

import static monads.HList.empty;
import static nederlands.woorden.Geslacht.*;
import static nederlands.woorden.Lidwoord.*;
import static nederlands.woorden.Valentie.*;

/**
 * Created by Toon Baeyens
 */
public class Nederlands {
    public final static Set<String> koppelwerkwoorden = new HashSet<String>() {{
        add("worden");
        add("zijn");
    }};

    public final static HList<Lidwoord> lidwoorden = HList.fromArray(EEN, DE, HET);

    public final static HList<Substantief> substatieven = HList.fromArray(
            new Substantief("hond", M),
            new Substantief("kip", V, "kippen", "kipje"),
            new Substantief("poes", V, "poezen", "poesje"),
            new Substantief("huis", O, "huizen", "huisje"),
            new Substantief("meisje", O, "meisjes", null),
            new Substantief("kus", M, "kussen", "kusje"),
            new Substantief("eten", O, false),
            new Substantief("melk", V, false)
    );

    public final static HList<Werkwoord> werkwoorden = HList.fromArray(
            new Werkwoord("loop", "lopen", ONOVERGANKELIJK),
            new Werkwoord("dans", "dansen", ONOVERGANKELIJK),
            new Werkwoord("word", "worden", ONOVERGANKELIJK),
            new Werkwoord("zing", "zingen", GEMENGD, "voor"),
            new Werkwoord("krijg", "krijgen", OVERGANKELIJK),
            new Werkwoord("geef", "geven", OVERGANKELIJK, "aan"),
            new Werkwoord("heb", "hebben", OVERGANKELIJK, null, new String[]{"heb", "hebt", "heeft"}),
            new Werkwoord("ben", "zijn", ONOVERGANKELIJK, null, new String[]{"ben", "bent", "is"})
    );

    public final static HList<Voornaamwoord> persoonlijke_voornaamwoorden = HList.fromArray(
            new Voornaamwoord("ik", 1, false),
            new Voornaamwoord("jij", 2, false),
            new Voornaamwoord("hij", 3, false, M),
            new Voornaamwoord("zij", 3, false, V),
            new Voornaamwoord("het", 3, false, O),
            new Voornaamwoord("wij", 1, true),
            new Voornaamwoord("jullie", 2, true),
            new Voornaamwoord("zij", 3, true)
    );

    // https://nl.wikipedia.org/wiki/Nederlandse_grammatica#Wederkerende_voornaamwoorden
    public final static HList<Voornaamwoord> persoonlijke_voorwerpsvorm = HList.fromArray(
            new Voornaamwoord("me", 1, false),
            new Voornaamwoord("mij", 1, false),
            new Voornaamwoord("je", 2, false),
            new Voornaamwoord("u", 2, false),
            new Voornaamwoord("hem", 3, false),
            new Voornaamwoord("haar", 3, false),
            new Voornaamwoord("het", 3, false), // TODO Niet na voorzetsel
            new Voornaamwoord("ons", 1, true),
            new Voornaamwoord("je", 2, true),
            new Voornaamwoord("u", 2, true),
            new Voornaamwoord("jullie", 2, true),
            new Voornaamwoord("hen", 3, true)
    );
    public final static Parser<Lidwoord> LIDWOORD = lidwoorden.foldr(lw -> p -> p.add(lw.getParser()), s -> empty());
    public final static Parser<Lidwoord> LIDWOORD_BEPAALD = lidwoorden.foldr(lw -> p -> lw.isBepaald() ? p.add(lw.getParser()) : p, s -> empty());
    public final static Parser<Lidwoord> LIDWOORD_ONBEPAALD = lidwoorden.foldr(lw -> p -> !lw.isBepaald() ? p.add(lw.getParser()) : p, s -> empty());
    public final static Parser<Werkwoord.Vervoeging> WERKWOORD = werkwoorden.foldr(ww -> p -> p.add(ww.getParser()), ww -> empty());
    public final static Parser<Substantief.Verbuiging> SUBSTANTIEF = substatieven.foldr(s -> p -> p.add(s.getParser()), s -> empty());
    public final static Parser<Substantief.Verbuiging> SUBSTANTIEF_TELBAAR = substatieven.foldr(s -> p -> s.isTelbaar() ? p.add(s.getParser()) : p, s -> empty());
    public final static Parser<Substantief.Verbuiging> SUBSTANTIEF_ONTELBAAR = substatieven.foldr(s -> p -> !s.isTelbaar() ? p.add(s.getParser()) : p, s -> empty());
    public final static Parser<Voornaamwoord> PERSOONLIJKE_VOORNAAMWOORD = persoonlijke_voornaamwoorden.foldr(vnw -> p -> p.add(vnw.getParser()), s -> empty());
    public final static Parser<Voornaamwoord> PERSOONLIJKE_VOORWERPSVORM = persoonlijke_voorwerpsvorm.foldr(vnw -> p -> p.add(vnw.getParser()), s -> empty());
}
