package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionFromSuffix;

/**
 * Created by tim on 8/24/14.
 */
public interface InflectionFactory {
    InflectionFromSuffix create(@Assisted(value = "entryWord") String entryWord,
                @Assisted(value = "suffixWithoutHyphen") String suffixWithoutHyphen,
                @Assisted(value = "completeInflection") String completeInflection);

    Inflection create(@Assisted(value = "entryWord") String entryWord,
                @Assisted(value = "completeInflection") String completeInflection);
}
