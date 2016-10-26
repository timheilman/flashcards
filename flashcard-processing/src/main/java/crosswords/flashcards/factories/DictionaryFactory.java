package crosswords.flashcards.factories;

import crosswords.flashcards.domain.Entry;

import java.util.Map;

public interface DictionaryFactory {
    Map<String, Entry> createDictionary();
}
