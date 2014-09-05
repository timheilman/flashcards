package crosswords.flashcards.domain.entry.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.AnnotatedInflectionEntry;
import crosswords.flashcards.domain.Entry;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by tim on 8/25/14.
 */
public class AnnotatedInflectionEntryImpl extends AnnotatedEntryImpl implements AnnotatedInflectionEntry {
    @Inject
    public AnnotatedInflectionEntryImpl(@Assisted(value = "e") Set<String> e,
                                        @Assisted(value = "w") Set<String> w,
                                        @Assisted(value = "o2") Set<String> o2,
                                        @Assisted(value = "o3_2") Set<String> o3_2,
                                        @Assisted(value = "o3_some3") Set<String> o3_some3,
                                        @Assisted String inflectionWord,
                                        @Assisted Entry entry) {
        super(e, w, o2, o3_2, o3_some3, entry);
        entryWord = inflectionWord;
    }

    @Override
    public List<String> getBlocksToPrint() {
        return Arrays.asList(getMembershipBlock(entryWord, ""),
                getMembershipBlock(entry.getEntryWord(), "r"),
                entry.getEntryWord(),
                getPartOfSpeechBlock(),
                getDefinitionBlock());
    }

    @Override
    public String getInflectionsBlock() {
        // we want them simply to be gone:
        return null;
    }
}
