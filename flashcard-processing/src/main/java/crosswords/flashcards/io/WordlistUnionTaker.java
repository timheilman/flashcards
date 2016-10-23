package crosswords.flashcards.io;


import crosswords.flashcards.factories.bindingannotations.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.*;

public class WordlistUnionTaker implements Provider<Iterator<String>> {

    private final SortedSet<String> unionNonSowpods = new TreeSet<String>();

    @Inject
    public WordlistUnionTaker(@Enable1 Set<String> e,
                              @WordsWithFriends4 Set<String> w,
                              @Otcwl2 Set<String> otcwl2,
                              @Otcwl2014TwoToEightLetterWords Set<String> otcwl2014_2_to_8lw) {
        unionNonSowpods.addAll(e);
        unionNonSowpods.addAll(w);
        unionNonSowpods.addAll(otcwl2);
        unionNonSowpods.addAll(otcwl2014_2_to_8lw);
    }

    @Override
    public Iterator<String> get() {
        return unionNonSowpods.iterator();
    }
}
