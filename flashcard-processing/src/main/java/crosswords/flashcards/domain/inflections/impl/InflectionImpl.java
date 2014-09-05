package crosswords.flashcards.domain.inflections.impl;


import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionCategory;
import crosswords.flashcards.domain.inflections.InflectionCategorizer;

import javax.inject.Inject;

/**
 * Created by tim on 8/24/14.
 */
public class InflectionImpl implements Inflection {
    protected final InflectionCategorizer inflectionCategorizer;
    private final String entryWord;
    private final String completeInflection;
    protected InflectionCategory inflectionCategory;

    /**
     *
     * @param entryWord
     * @param completeInflection
     */
    @Inject
    public InflectionImpl(InflectionCategorizer inflectionCategorizer,
                          @Assisted(value = "entryWord") String entryWord,
                          @Assisted(value = "completeInflection") String completeInflection) {
        this.inflectionCategorizer = inflectionCategorizer;
        this.entryWord = entryWord;
        this.completeInflection = completeInflection;
    }

    public String getEntryWord() {
        return entryWord;
    }

    public String getCompleteInflection() {
        return completeInflection;
    }

    @Override
    public InflectionCategory getCategory() {
        if (this.inflectionCategory == null) {
            this.inflectionCategory = inflectionCategorizer.categorize(this);
        }
        return inflectionCategory;
    }

    @Override
    public boolean wasSpecifiedWithSuffix() {
        return false;
    }

    @Override
    public  String getDisplayString() { return completeInflection; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InflectionImpl inflection = (InflectionImpl) o;

        if (!completeInflection.equals(inflection.completeInflection)) return false;
        if (!entryWord.equals(inflection.entryWord)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entryWord.hashCode();
        result = 31 * result + completeInflection.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "entryWord='" + entryWord + '\'' +
                ", completeInflection='" + completeInflection + '\'' +
                ", inflectionCategory=" + getCategory() +
                '}';
    }
}
