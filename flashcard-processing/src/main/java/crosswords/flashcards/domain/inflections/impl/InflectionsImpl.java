package crosswords.flashcards.domain.inflections.impl;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.domain.inflections.InflectionComparator;

import java.util.TreeSet;

public class InflectionsImpl extends TreeSet<Inflection> implements Inflections {


    @Inject
    public InflectionsImpl(InflectionComparator inflectionComparator) {
        super(inflectionComparator);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Inflection inflection : this) {
            stringBuilder.append(inflection);
            stringBuilder.append(",\n");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
