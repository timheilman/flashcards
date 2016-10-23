package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.Entry;

import java.util.Map;

public interface DictionaryFactory {
    Map<String, Entry> createDictionary();
}
