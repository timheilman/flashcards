package crosswords.flashcards.domain.inflections;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.factories.InflectionsFactory;

/**
 * Created by tim on 8/23/14.
 */
public class InflectionsFieldFromSowpodsEntryParser {
    private final InflectionMakerFromEntryAndStringInAsterisks inflectionMakerFromEntryAndStringInAsterisks;
    private final InflectionsFactory inflectionsFactory;

    @Inject
    public InflectionsFieldFromSowpodsEntryParser(InflectionMakerFromEntryAndStringInAsterisks inflectionMakerFromEntryAndStringInAsterisks,
                                                  InflectionsFactory inflectionsFactory) {
        this.inflectionMakerFromEntryAndStringInAsterisks = inflectionMakerFromEntryAndStringInAsterisks;
        this.inflectionsFactory = inflectionsFactory;
    }

    public Inflections parse(String entryWord, String inflectionsField) {
        Inflections inflections = inflectionsFactory.create();
        String[] inflectionsWithJunk = inflectionsField.split("\\*");
        for (String inflectionOrJunk : inflectionsWithJunk) {
            if (inflectionOrJunk.matches("^[-A-Z]+$")) {
                inflections.add(inflectionMakerFromEntryAndStringInAsterisks.get(entryWord, inflectionOrJunk));
            }
        }
        return inflections;
    }
}
