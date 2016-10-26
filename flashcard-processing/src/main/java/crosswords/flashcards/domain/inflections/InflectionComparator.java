package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionCategory;

public class InflectionComparator implements java.util.Comparator<Inflection> {

    public int compare(Inflection inflection, Inflection inflection2) {
        InflectionCategory inflectionCategory = inflection.getCategory();
        InflectionCategory inflectionCategory2 = inflection2.getCategory();
        if (inflectionCategory.equals(inflectionCategory2)) {
            if (inflection.wasSpecifiedWithSuffix() == inflection2.wasSpecifiedWithSuffix()) {
                if (inflection.getCompleteInflection().length() == inflection2.getCompleteInflection().length()) {
                    return inflection.getCompleteInflection().compareTo(inflection2.getCompleteInflection());
                } else {
                    return inflection.getCompleteInflection().length() - inflection2.getCompleteInflection().length();
                }
            } else {
                return inflection.wasSpecifiedWithSuffix() ? -1 : 1;
            }
        } else {
            return inflectionCategory.compareTo(inflectionCategory2);
        }
    }
}
