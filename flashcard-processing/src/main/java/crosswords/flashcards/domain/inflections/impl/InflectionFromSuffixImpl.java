package crosswords.flashcards.domain.inflections.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.InflectionFromSuffix;
import crosswords.flashcards.domain.InflectionCategory;
import crosswords.flashcards.domain.inflections.InflectionCategorizer;

public class InflectionFromSuffixImpl extends InflectionImpl implements InflectionFromSuffix {
    /**
     * @param entryWord
     * @param suffixWithoutHyphen
     * @param completeInflection
     */
    @Inject
    public InflectionFromSuffixImpl(InflectionCategorizer inflectionCategorizer,
                                    @Assisted(value = "entryWord") String entryWord,
                                    @Assisted(value = "suffixWithoutHyphen") String suffixWithoutHyphen,
                                    @Assisted(value = "completeInflection") String completeInflection) {
        super(inflectionCategorizer, entryWord, completeInflection);
        this.suffixWithoutHyphen = suffixWithoutHyphen;
    }

    private final String suffixWithoutHyphen;

    public String getSuffixWithoutHyphen() {
        return suffixWithoutHyphen;
    }

    @Override
    public InflectionCategory getCategory() {
        if (inflectionCategory == null) {
            this.inflectionCategory = inflectionCategorizer.categorize(this.getSuffixWithoutHyphen());
        }
        return inflectionCategory;
    }

    @Override
    public boolean wasSpecifiedWithSuffix() {
        return true;
    }

    @Override
    public  String getDisplayString() { return "-" + suffixWithoutHyphen; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InflectionFromSuffixImpl that = (InflectionFromSuffixImpl) o;

        if (!suffixWithoutHyphen.equals(that.suffixWithoutHyphen)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + suffixWithoutHyphen.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "entryWord='" + getEntryWord() + '\'' +
                ", suffixWithoutHyphen='" + suffixWithoutHyphen + '\'' +
                ", completeInflection='" + getCompleteInflection() + '\'' +
                ", inflectionCategory=" + getCategory() +
                '}';
    }
}
