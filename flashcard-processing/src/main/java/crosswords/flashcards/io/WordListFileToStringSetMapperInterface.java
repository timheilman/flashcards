package crosswords.flashcards.io;

import javax.inject.Provider;
import java.util.Set;

public interface WordListFileToStringSetMapperInterface extends Provider<Set<String>> {
    Set<String> getPerformantSet(String fileName);
}
