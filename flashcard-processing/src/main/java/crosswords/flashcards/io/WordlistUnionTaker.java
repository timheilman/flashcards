package crosswords.flashcards.io;


import crosswords.flashcards.factories.bindingannotations.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by tim on 9/6/14.
 */
public class WordlistUnionTaker implements Provider<SortedSet<String>> {

    private final SortedSet<String> unionNonSowpods = new TreeSet<String>();

    @Inject
    public WordlistUnionTaker(@Enable1 Set<String> e,
                              @WordsWithFriends4 Set<String> w,
                              @Otcwl2 Set<String> otcwl2,
                              @Otcwl2014TwoLetterWords Set<String> otcwl2014_2lw,
                              @Otcwl2014ThreeFromTwo Set<String> otcwl2014_some3LW) {
        unionNonSowpods.addAll(e);
        unionNonSowpods.addAll(w);
        unionNonSowpods.addAll(otcwl2);
        unionNonSowpods.addAll(otcwl2014_2lw);
        unionNonSowpods.addAll(otcwl2014_some3LW);
    }

    @Override
    public SortedSet<String> get() {
        return unionNonSowpods;
    }
}
