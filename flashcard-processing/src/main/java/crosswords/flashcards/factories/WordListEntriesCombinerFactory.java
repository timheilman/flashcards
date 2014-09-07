package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.annotateddictionary.WordListEntriesCombiner;

/**
 * Created by tim on 8/25/14.
 */
public interface WordListEntriesCombinerFactory {
    public WordListEntriesCombiner create();
}
