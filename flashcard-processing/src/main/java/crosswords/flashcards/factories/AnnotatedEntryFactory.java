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
    public AnnotatedEntry createForEntryWord(@Assisted(value = "e") Set<String> e,
                                             @Assisted(value = "w") Set<String> w,
                                             @Assisted(value = "o2") Set<String> o2,
                                             @Assisted(value = "o3_2") Set<String> o3_2,
                                             @Assisted(value = "o3_some3") Set<String> o3_some3,
                                             @Assisted Entry entry);
    public AnnotatedInflectionEntry createForInflection(@Assisted(value = "e") Set<String> e,
                                                        @Assisted(value = "w") Set<String> w,
                                                        @Assisted(value = "o2") Set<String> o2,
                                                        @Assisted(value = "o3_2") Set<String> o3_2,
                                                        @Assisted(value = "o3_some3") Set<String> o3_some3,
                                                        @Assisted String inflection,
                                                        @Assisted Entry entry);
}
