package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.PartOfSpeech;
import crosswords.flashcards.domain.Inflections;

import javax.annotation.Nullable;

/**
 * Created by tim on 8/24/14.
 */
public interface EntryFactory {
    Entry create(@Assisted(value = "entryWord") String entryWord,
                 @Nullable @Assisted PartOfSpeech partOfSpeech,
                 @Nullable @Assisted Inflections inflections,
                 @Nullable @Assisted(value = "definition") String definition);
}
