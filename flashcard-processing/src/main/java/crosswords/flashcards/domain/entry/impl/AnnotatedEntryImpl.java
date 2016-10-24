package crosswords.flashcards.domain.entry.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import crosswords.flashcards.domain.*;
import crosswords.flashcards.factories.bindingannotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotatedEntryImpl implements AnnotatedEntry {
    public static final int MAX_LENGTH_OF_KNOWN_OTCWL2014_MEMBERSHIP = 8;
    protected String entryWord;
    protected final Entry entry;
    protected final Set<String> enable1;
    protected final Set<String> wwf4;
    protected final Set<String> o2;
    protected final Set<String> o3_2_to_8;

    @Inject
    // todo: comb code for IDE warnings and eliminate them systematically: go all-green
    // todo: test coverage in build system
    // todo: get test coverage to ~100%
    // todo: log-scale file length histogram as part of pre-commit hooks? build?
    // todo: eliminate long data lists in code
    // todo: separate value objects from service objects; what's done in formatForPrinting should be done in a factory
    // todo: feature-make it easy to configure: I don't care about enable1 but I want sowpods in output
    // todo: feature-add sowpods
    // todo: feature-make cloze cards for inflection categories
    // todo: feature-heuristic to include ABAMPS in sowpods_dict_words: check other whole-word inflections for the missing S

    public AnnotatedEntryImpl(@Enable1 Set<String> e,
                              @WordsWithFriends4 Set<String> w,
                              @Otcwl2 Set<String> o2,
                              @Otcwl2014TwoToEightLetterWords Set<String> o3_2_to_8,
                              @Assisted Entry entry) {
        enable1 = e;
        wwf4 = w;
        this.o2 = o2;
        this.o3_2_to_8 = o3_2_to_8;
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
            if (perhapsMember.length() >= 2 && perhapsMember.length() <= MAX_LENGTH_OF_KNOWN_OTCWL2014_MEMBERSHIP) {
                potentiallyUnknownMembershipFlags.append(String.format(" %s%so3", o3_2_to_8.contains(perhapsMember) ? "+" : "-",  prefix));
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
            if (o2.contains(completeInflection) || o3_2_to_8.contains(completeInflection)) {
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
