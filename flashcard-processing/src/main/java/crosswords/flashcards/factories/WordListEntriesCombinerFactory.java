package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.annotateddictionary.WordListEntriesCombiner;

public interface WordListEntriesCombinerFactory {
    public WordListEntriesCombiner create();
}
