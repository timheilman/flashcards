package crosswords.flashcards.io;

import javax.inject.Provider;
import java.util.Set;

/**
 * Copyright NWEA
 */
public interface WordListFileToStringSetMapperInterface extends Provider<Set<String>> {
    Set<String> getPerformantSet(String fileName);
}
