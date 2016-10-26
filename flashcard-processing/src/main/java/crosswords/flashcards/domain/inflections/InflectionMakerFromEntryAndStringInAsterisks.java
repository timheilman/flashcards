package crosswords.flashcards.domain.inflections;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.factories.InflectionFactory;

class InflectionMakerFromEntryAndStringInAsterisks {
    private final InflectionFactory inflectionFactory;

    @Inject
    InflectionMakerFromEntryAndStringInAsterisks(InflectionFactory inflectionFactory) {
        this.inflectionFactory = inflectionFactory;
    }

    public Inflection get(String entryWord, String stringWithinAsterisks) {
        if (entryWord.length() < 2
                || stringWithinAsterisks.length() < 2
                || entryWord.matches("[^A-Z]")
                || stringWithinAsterisks.matches("[^-A-Z]")
                || stringWithinAsterisks.substring(1, stringWithinAsterisks.length()).matches("[^A-Z]")) {
            throwException(entryWord, stringWithinAsterisks);
        }
        if ("-".equals(stringWithinAsterisks.substring(0, 1))) {
            String suffixWithoutHyphen = stringWithinAsterisks.substring(1, stringWithinAsterisks.length());
            return inflectionFactory.create(entryWord, suffixWithoutHyphen, entryWord + suffixWithoutHyphen);
        } else {
            return inflectionFactory.create(entryWord, stringWithinAsterisks);
        }
    }

    private Inflection throwException(String entryWord, String stringWithinAsterisks) {
        throw new IllegalArgumentException("see source for preconditions; entryWord: `" + entryWord + "'; stringWithinAsterisks: `" + stringWithinAsterisks + "'");
    }
}
