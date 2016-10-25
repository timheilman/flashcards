package crosswords.flashcards.domain.entry.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.factories.bindingannotations.Enable1;
import crosswords.flashcards.factories.bindingannotations.Otcwl2;
import crosswords.flashcards.factories.bindingannotations.Otcwl2014TwoToEightLetterWords;
import crosswords.flashcards.factories.bindingannotations.WordsWithFriends4;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotatedInflectionEntryImpl extends AnnotatedEntryImpl implements AnnotatedInflectionEntry {
    @Inject
    public AnnotatedInflectionEntryImpl(@Enable1 Set<String> e,
                                        @WordsWithFriends4 Set<String> w,
                                        @Otcwl2 Set<String> o2,
                                        @Otcwl2014TwoToEightLetterWords Set<String> o3_2_to_8,
                                        @Assisted String inflectionWord,
                                        @Assisted Entry entry) {
        super(e, w, o2, o3_2_to_8, entry);
        entryWord = inflectionWord;
    }

    @Override
    protected List<String> getBlocksToPrint() {
        return Arrays.asList(getMembershipBlock(entryWord, ""),
                getMembershipBlock(entry.getEntryWord(), "r"),
                entry.getEntryWord(),
                getPartOfSpeechBlock(),
                getDefinitionBlock());
    }

    @Override
    protected String getInflectionsBlock() {
        // we want them simply to be gone:
        return null;
    }
}
