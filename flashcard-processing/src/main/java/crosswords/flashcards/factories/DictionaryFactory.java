package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Entry;

import java.util.Map;

/**
 * Created by tim on 8/25/14.
 */
public interface DictionaryFactory {
    Map<String, Entry> createDictionary();
}
