package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;

public interface AnnotatedEntryFactory {
    AnnotatedEntry createForEntryWord(@Assisted Entry entry);

    AnnotatedInflectionEntry createForInflection(@Assisted String inflection,
                                                 @Assisted Entry entry);
}
