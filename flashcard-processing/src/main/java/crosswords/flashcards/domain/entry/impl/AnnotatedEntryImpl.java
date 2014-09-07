package crosswords.flashcards.domain.entry.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.*;
import crosswords.flashcards.factories.bindingannotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by tim on 8/25/14.
 */
public class AnnotatedEntryImpl implements AnnotatedEntry {
    protected String entryWord;
    protected final Entry entry;
    protected final Set<String> enable1;
    protected final Set<String> wwf4;
    protected final Set<String> o2;
    protected final Set<String> o3_2_to_4;

    @Inject
    // todo: separate value objects from service objects; what's done in formatForPrinting should be done in a factory
    // todo: add long list?
    // todo: add sowpods?
    // todo: study at home to be able to regenerate decks quickly!
    // todo: make cloze cards for inflection categories

    public AnnotatedEntryImpl(@Enable1 Set<String> e,
                              @WordsWithFriends4 Set<String> w,
                              @Otcwl2 Set<String> o2,
                              @Otcwl2014TwoToFourLetterWords Set<String> o3_2_to_4,
                              @Assisted Entry entry) {
        enable1 = e;
        wwf4 = w;
        this.o2 = o2;
        this.o3_2_to_4 = o3_2_to_4;
        this.entryWord = entry.getEntryWord();
        this.entry = entry;
    }

    @Override
    public String formatForPrinting() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(entryWord).append('\t');
        for (String blockToPrint : getBlocksToPrint()) {
            if (blockToPrint != null) {
                stringBuilder.append(' ').append(blockToPrint);
            }
        }
        return stringBuilder.toString();
    }

    protected String getMembershipBlock(String perhapsMember, String prefix) {
        StringBuilder potentiallyUnknownMembershipFlags = new StringBuilder();
        // we know for certain regarding otcwl2 (o2)
        if (o2.contains(perhapsMember)) {
            // they assure us there were no deletions from otcwl2 (o2) to otcwl2014 (o3):
            potentiallyUnknownMembershipFlags.append(String.format(" +%so2 +%so3", prefix, prefix));
        } else {
            potentiallyUnknownMembershipFlags.append(String.format(" -%so2", prefix));
            if (perhapsMember.length() >= 2 && perhapsMember.length() <= 4) {
                potentiallyUnknownMembershipFlags.append(String.format(" %s%so3", o3_2_to_4.contains(perhapsMember) ? "+" : "-",  prefix));
            }
        }
        return String.format("%s%se1 %s%sw4%s",
                enable1.contains(perhapsMember) ? "+" : "-", prefix,
                wwf4.contains(perhapsMember) ? "+" : "-", prefix,
                potentiallyUnknownMembershipFlags.toString());
    }

    protected String getPartOfSpeechBlock() {
        return entry.getPartOfSpeech() == null ? null : "_" + entry.getPartOfSpeech().getDisplayString() + "_";
    }

    protected String getInflectionsBlock() {
        Inflections inflections = entry.getInflections();
        if (inflections == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (Inflection inflection : inflections) {
            String completeInflection = inflection.getCompleteInflection();
            // INFLECTION INCLUSION DECISION:
            if (o2.contains(completeInflection) || o3_2_to_4.contains(completeInflection)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append('*').append(inflection.getDisplayString()).append('*');
            }
        }
        return stringBuilder.toString();
    }

    protected String getDefinitionBlock() {
        String definition = entry.getDefinition();
        return definition == null ? "[no defn]" : definition;
    }

    protected List<String> getBlocksToPrint() {
        return Arrays.asList(getMembershipBlock(entryWord, ""),
                getPartOfSpeechBlock(),
                getInflectionsBlock(),
                getDefinitionBlock());
    }
}
