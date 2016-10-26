package crosswords.flashcards.domain.entry.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.domain.PartOfSpeech;

import javax.annotation.Nullable;

public class EntryImpl implements Entry {
    private final String entryWord;
    private final PartOfSpeech partOfSpeech;
    private final Inflections inflections;
    private final String definition;

    @Inject
    public EntryImpl(@Assisted(value = "entryWord") String entryWord,
                     @Assisted @Nullable PartOfSpeech partOfSpeech,
                     @Assisted @Nullable Inflections inflections,
                     @Assisted(value = "definition") @Nullable String definition) {
        this.entryWord = entryWord;
        this.partOfSpeech = partOfSpeech;
        this.inflections = inflections;
        this.definition = definition;
    }

    public String getEntryWord() {
        return entryWord;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public Inflections getInflections() {
        return inflections;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryImpl entry = (EntryImpl) o;

        return entryEqual(entry) && entryWordEqual(entry)
                && partOfSpeechEqual(entry) && inflectionsEqual(entry);

    }

    private boolean partOfSpeechEqual(EntryImpl entry) {
        return partOfSpeech == entry.partOfSpeech;
    }

    private boolean entryWordEqual(EntryImpl entry) {
        return entryWord.equals(entry.entryWord);
    }

    private boolean inflectionsEqual(EntryImpl entry) {
        return inflections != null ? inflections.equals(entry.inflections) : entry.inflections == null;
    }

    private boolean entryEqual(EntryImpl entry) {
        return definition != null ? definition.equals(entry.definition) : entry.definition == null;
    }

    @Override
    public int hashCode() {
        int result = entryWord.hashCode();
        result = 31 * result + (partOfSpeech != null ? partOfSpeech.hashCode() : 0);
        result = 31 * result + (inflections != null ? inflections.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntryImpl{" +
                "entryWord='" + entryWord + '\'' +
                ", partOfSpeech=" + partOfSpeech +
                ", inflections=" + inflections +
                ", definition='" + definition + '\'' +
                '}';
    }
}
