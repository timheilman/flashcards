package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionCategory;

import java.util.Arrays;
import java.util.List;

import static crosswords.flashcards.domain.InflectionCategory.*;

/**
 * Created by tim on 8/24/14.
 */
public class InflectionCategorizer {

    private final static List<String> descendingLengthSuffixes = Arrays.asList("LESSNESSES",
            "ABILITIES",
            "LESSNESS",
            "ABLENESS",
            "INGNESS",
            "INESSES",
            "ABILITY",
            "ISHNESS",
            "FULNESS",
            "ABILITY",
            "EDNESS",
            "LESSLY",
            "ICALLY",
            "NESSES",
            "ATIONS",
            "TINGS",
            "PINGS",
            "LINGS",
            "GINGS",
            "NINGS",
            "BINGS",
            "MINGS",
            "DINGS",
            "RINGS",
            "INGLY",
            "GIEST",
            "BIEST",
            "INESS",
            "TABLE",
            "PABLE",
            "ISHLY",
            "CEOUS",
            "OUSLY",
            "ATIVE",
            "IVELY",
            "FULLY",
            "SHIPS",
            "HOODS",
            "ESSES",
            "DNESS",
            "MENTS",
            "ITIES",
            "ATION",
            "TING",
            "PING",
            "LING",
            "GING",
            "NING",
            "BING",
            "MING",
            "DING",
            "RING",
            "KING",
            "SING",
            "INGS",
            "EDLY",
            "LERS",
            "TERS",
            "GERS",
            "PERS",
            "MERS",
            "DERS",
            "BERS",
            "NERS",
            "RERS",
            "TEST",
            "LEST",
            "MEST",
            "DEST",
            "GIER",
            "BIER",
            "IEST",
            "LESS",
            "LIKE",
            "ABLE",
            "IBLE",
            "ABLY",
            "ALLY",
            "ATIC",
            "ICAL",
            "TOUS",
            "SOME",
            "FULS",
            "DOMS",
            "SHIP",
            "HOOD",
            "NESS",
            "MENT",
            "ISMS",
            "ISTS",
            "IONS",
            "RIES",
            "AGES",
            "SES",
            "ZES",
            "NES",
            "TED",
            "LED",
            "PED",
            "GED",
            "NED",
            "BED",
            "MED",
            "DED",
            "RED",
            "KED",
            "SED",
            "ING",
            "DLY",
            "ERS",
            "ORS",
            "LER",
            "TER",
            "GER",
            "PER",
            "MER",
            "DER",
            "BER",
            "NER",
            "RER",
            "EST",
            "IES",
            "ILY",
            "IER",
            "IAL",
            "ALS",
            "TIC",
            "ISH",
            "OUS",
            "IVE",
            "ARY",
            "IAN",
            "INE",
            "OID",
            "FUL",
            "DOM",
            "ESS",
            "EES",
            "MAN",
            "MEN",
            "ITY",
            "ISM",
            "IST",
            "ION",
            "ERY",
            "AGE",
            "ATE",
            "ES",
            "ED",
            "ER",
            "RS",
            "OR",
            "ST",
            "LY",
            "GY",
            "BY",
            "TY",
            "AL",
            "IC",
            "EE",
            "RY",
            "TA",
            "EN",
            "TE",
            "IM",
            "AN",
            "NE",
            "TH",
            "S",
            "X",
            "D",
            "R",
            "Y",
            "E",
            "N",
            "L",
            "T",
            "H",
            "A",
            "I",
            "G"
    );

    /**
     * For use on complete inflections; heuristic.
     *
     * @param inflection
     * @return
     */
    public InflectionCategory categorize(Inflection inflection) {
        String completeInflection = inflection.getCompleteInflection();
        String entryWord = inflection.getEntryWord();

        SuffixAndCategory suffixAndCategoryMatchingCompleteInflection = findSuffixCategoryMatchingCompleteInflection(completeInflection);
        if (MISC_FREQ_10_OR_UNDER_SOWPODS.equals(suffixAndCategoryMatchingCompleteInflection.inflectionCategory)) {
            return ALTERNATE_SPELLING;
        }

        // we have some suffix match on the complete inflection
        // give it TWO pre-suffix characters to differ; are rest same?   yes: act as tho that suffix. no: act as alternate spelling
        if (completeInflection.length() < entryWord.length()) {
            return SHORTENING;
        }
        if (entryWord.substring(0, entryWord.length() - 2).equals(completeInflection.substring(0, entryWord.length() - 2))) {
            String guessedSuffix = suffixAndCategoryMatchingCompleteInflection.suffix;
            if (entryWord.endsWith("Y") && Y_THEN_PLURALS_LY_ER_EST_NOUN.equals(suffixAndCategoryMatchingCompleteInflection.inflectionCategory)
                    && guessedSuffix.contains("I")) {
                suffixAndCategoryMatchingCompleteInflection = findSuffixCategoryMatchingCompleteInflection(guessedSuffix.substring(guessedSuffix.indexOf("I") + 1, guessedSuffix.length()));
                if (MISC_FREQ_10_OR_UNDER_SOWPODS.equals(suffixAndCategoryMatchingCompleteInflection.inflectionCategory)) {
                    return ALTERNATE_SPELLING;
                } else {
                    return suffixAndCategoryMatchingCompleteInflection.inflectionCategory;
                }
            }
            return suffixAndCategoryMatchingCompleteInflection.inflectionCategory;
        }


        return InflectionCategory.ALTERNATE_SPELLING;
    }

    /**
     * For use if it was a suffix inflection.
     *
     * @param suffix
     * @return
     */
    public InflectionCategory categorize(String suffix) {
        if (false
                || "S".equals(suffix)
                || "ES".equals(suffix)
                || "SES".equals(suffix)
                || "ZES".equals(suffix)
                || "NES".equals(suffix)
                || "X".equals(suffix)
                ) {
            return InflectionCategory.PLURALIZATION_AND_CONJUGATION_WITH_S;
        }
        if (false
                || "ED".equals(suffix)
                || "D".equals(suffix)
                || "TED".equals(suffix)
                || "LED".equals(suffix)
                || "PED".equals(suffix)
                || "GED".equals(suffix)
                || "NED".equals(suffix)
                || "BED".equals(suffix)
                || "MED".equals(suffix)
                || "DED".equals(suffix)
                || "RED".equals(suffix)
                || "KED".equals(suffix)
                || "SED".equals(suffix)
                ) {
            return InflectionCategory.ED;
        }
        if (false
                || "ING".equals(suffix)
                || "TING".equals(suffix)
                || "PING".equals(suffix)
                || "LING".equals(suffix)
                || "GING".equals(suffix)
                || "NING".equals(suffix)
                || "BING".equals(suffix)
                || "MING".equals(suffix)
                || "DING".equals(suffix)
                || "RING".equals(suffix)
                || "KING".equals(suffix)
                || "SING".equals(suffix)
                ) {
            return InflectionCategory.ING;
        }
        if (false
                || "INGS".equals(suffix)
                || "TINGS".equals(suffix)
                || "PINGS".equals(suffix)
                || "LINGS".equals(suffix)
                || "GINGS".equals(suffix)
                || "NINGS".equals(suffix)
                || "BINGS".equals(suffix)
                || "MINGS".equals(suffix)
                || "DINGS".equals(suffix)
                || "RINGS".equals(suffix)
                ) {
            return InflectionCategory.INGS;
        }
        if (false
                || "INGLY".equals(suffix)
                || "INGNESS".equals(suffix)
                || "EDLY".equals(suffix)
                || "DLY".equals(suffix)
                || "EDNESS".equals(suffix)
                ) {
            return InflectionCategory.ED_ING_THEN_LY_AND_NESS;
        }
        if (false
                || "ER".equals(suffix)
                || "ERS".equals(suffix)
                || "R".equals(suffix)
                || "RS".equals(suffix)
                || "OR".equals(suffix)
                || "ORS".equals(suffix)
                || "LER".equals(suffix)
                || "LERS".equals(suffix)
                || "TER".equals(suffix)
                || "TERS".equals(suffix)
                || "GER".equals(suffix)
                || "GERS".equals(suffix)
                || "PER".equals(suffix)
                || "PERS".equals(suffix)
                || "MER".equals(suffix)
                || "MERS".equals(suffix)
                || "DER".equals(suffix)
                || "DERS".equals(suffix)
                || "BER".equals(suffix)
                || "BERS".equals(suffix)
                || "NER".equals(suffix)
                || "NERS".equals(suffix)
                || "RER".equals(suffix)
                || "RERS".equals(suffix)
                ) {
            return InflectionCategory.ER_AND_ERS;
        }
        if (false
                || "EST".equals(suffix)
                || "TEST".equals(suffix)
                || "ST".equals(suffix)
                || "LEST".equals(suffix)
                || "MEST".equals(suffix)
                || "DEST".equals(suffix)
                ) {
            return InflectionCategory.EST;
        }
        if (false
                || "LY".equals(suffix)
                ) {
            return InflectionCategory.LY;
        }
        if (false
                || "Y".equals(suffix)
                || "GY".equals(suffix)
                || "BY".equals(suffix)
                || "TY".equals(suffix)
                || "RY".equals(suffix)
                || "ERY".equals(suffix)
                || "IES".equals(suffix)
                || "ILY".equals(suffix)
                || "IER".equals(suffix)
                || "RIES".equals(suffix)
                || "GIER".equals(suffix)
                || "BIER".equals(suffix)
                || "IEST".equals(suffix)
                || "GIEST".equals(suffix)
                || "BIEST".equals(suffix)
                || "INESS".equals(suffix)
                || "INESSES".equals(suffix)
                ) {
            return InflectionCategory.Y_THEN_PLURALS_LY_ER_EST_NOUN;
        }
        if (false
                || "LESS".equals(suffix)
                || "LESSLY".equals(suffix)
                || "LESSNESS".equals(suffix)
                || "LESSNESSES".equals(suffix)

                || "LIKE".equals(suffix)
                ) {
            return InflectionCategory.LESS_AND_LIKE;
        }
        if (false
                || "ABLE".equals(suffix)
                || "TABLE".equals(suffix)
                || "PABLE".equals(suffix)
                || "IBLE".equals(suffix)
                || "ABLY".equals(suffix)
                || "ABLENESS".equals(suffix)
                || "ABILITY".equals(suffix)
                || "ABILITIES".equals(suffix)

                || "AL".equals(suffix)
                || "IAL".equals(suffix)
                || "ALS".equals(suffix)
                || "ALLY".equals(suffix)

                || "IC".equals(suffix)
                || "ATIC".equals(suffix)
                || "TIC".equals(suffix)
                || "ICAL".equals(suffix)
                || "ICALLY".equals(suffix)

                || "ISH".equals(suffix)
                || "ISHLY".equals(suffix)
                || "ISHNESS".equals(suffix)

                || "OUS".equals(suffix)
                || "TOUS".equals(suffix)
                || "CEOUS".equals(suffix)
                || "OUSLY".equals(suffix)

                || "IVE".equals(suffix)
                || "ATIVE".equals(suffix)
                || "IVELY".equals(suffix)

                || "AN".equals(suffix)
                || "ARY".equals(suffix)
                || "IAN".equals(suffix)
                || "INE".equals(suffix)
                || "SOME".equals(suffix)
                || "OID".equals(suffix)
                ) {
            return InflectionCategory.OTHER_ADJ_THEN_LY_NOUN_PLNOUN;
        }
        if (false
                || "FUL".equals(suffix)
                || "FULS".equals(suffix)
                || "FULLY".equals(suffix)
                || "FULNESS".equals(suffix)
                ) {
            return InflectionCategory.FUL_AND_CO;
        }
        if (false
                || "DOM".equals(suffix)
                || "DOMS".equals(suffix)
                || "SHIP".equals(suffix)
                || "SHIPS".equals(suffix)
                || "HOOD".equals(suffix)
                || "HOODS".equals(suffix)
                || "ESS".equals(suffix)
                || "ESSES".equals(suffix)
                || "EE".equals(suffix)
                || "EES".equals(suffix)
                || "MAN".equals(suffix)
                || "MEN".equals(suffix)
                ) {
            return InflectionCategory.PEOPLE_NOUNS_INCL_PLNOUN;
        }
        if (false
                || "NESS".equals(suffix)
                || "NESSES".equals(suffix)
                || "DNESS".equals(suffix)
                || "MENT".equals(suffix)
                || "MENTS".equals(suffix)
                || "ITY".equals(suffix)
                || "ITIES".equals(suffix)
                || "ISM".equals(suffix)
                || "ISMS".equals(suffix)
                || "IST".equals(suffix)
                || "ISTS".equals(suffix)
                || "ION".equals(suffix)
                || "IONS".equals(suffix)
                || "ATION".equals(suffix)
                || "ATIONS".equals(suffix)
                || "ABILITY".equals(suffix)
                || "AGE".equals(suffix)
                || "AGES".equals(suffix)
                || "ATE".equals(suffix)
                ) {
            return InflectionCategory.OTHER_NOUNS_INCL_PLNOUN;
        }
        if (false
                || "E".equals(suffix)
                || "N".equals(suffix)
                || "L".equals(suffix)
                || "TA".equals(suffix)
                || "T".equals(suffix)
                || "H".equals(suffix)
                || "EN".equals(suffix)
                || "TE".equals(suffix)
                || "IM".equals(suffix)
                || "A".equals(suffix)
                || "I".equals(suffix)
                || "NE".equals(suffix)
                || "G".equals(suffix)
                || "TH".equals(suffix)
                ) {
            return InflectionCategory.MISC_FREQ_OVER_10_SOWPODS;
        }
        return InflectionCategory.MISC_FREQ_10_OR_UNDER_SOWPODS;
    }

    private class SuffixAndCategory {
        private final String suffix;
        private final InflectionCategory inflectionCategory;

        public SuffixAndCategory(String suffix, InflectionCategory inflectionCategory) {
            this.suffix = suffix;
            this.inflectionCategory = inflectionCategory;
        }
    }

    private SuffixAndCategory findSuffixCategoryMatchingCompleteInflection(String completeInflection) {
        for (String suffix : descendingLengthSuffixes) {
            if (completeInflection.endsWith(suffix)) {
                return new SuffixAndCategory(suffix, categorize(suffix));
            }
        }

        return new SuffixAndCategory("", MISC_FREQ_10_OR_UNDER_SOWPODS);
    }
}
