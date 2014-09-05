package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;

import java.util.Set;

/**
 * Created by tim on 8/25/14.
 */
public interface AnnotatedEntryFactory {
    public AnnotatedEntry createForEntryWord(@Assisted Entry entry);
    public AnnotatedInflectionEntry createForInflection(@Assisted String inflection,
                                                        @Assisted Entry entry);
}
