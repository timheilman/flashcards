package crosswords.flashcards.domain.entry;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.PartOfSpeech;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.domain.inflections.InflectionsFieldFromSowpodsEntryParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SowpodsEntryParser {
    private static final Pattern startsWithPos = Pattern.compile("^ _([^_]*)_");
    private static final Pattern startsWithInflections = Pattern.compile("^ (\\*[-A-Z, *]*\\*)");
    private static final Pattern startsWithSpaces = Pattern.compile("^ *");
    private static final Pattern endsWithWhitespace = Pattern.compile("\\s*$");
    private final EntryFactory entryFactory;
    private final InflectionsFieldFromSowpodsEntryParser inflectionsFieldFromSowpodsEntryParser;
    private final PartOfSpeechParser partOfSpeechParser;

    @Inject
    public SowpodsEntryParser(EntryFactory entryFactory,
                              InflectionsFieldFromSowpodsEntryParser inflectionsFieldFromSowpodsEntryParser,
                              PartOfSpeechParser partOfSpeechParser) {
        this.entryFactory = entryFactory;
        this.inflectionsFieldFromSowpodsEntryParser = inflectionsFieldFromSowpodsEntryParser;
        this.partOfSpeechParser = partOfSpeechParser;
    }

    public Entry parse(String entryString) {
        String[] splitOnTabs = entryString.split("\\t");
        String entryWord = splitOnTabs[0];
        String remainder = splitOnTabs[1];

        PartOfSpeech partOfSpeech = null;
        Matcher startsWithPosMatcher = startsWithPos.matcher(remainder);
        if (startsWithPosMatcher.find()) {
            partOfSpeech = partOfSpeechParser.parse(startsWithPosMatcher.group(1));
            remainder = remainder.substring(startsWithPosMatcher.end());
        }

        Inflections inflections = null;
        Matcher startsWithInflectionsMatcher = startsWithInflections.matcher(remainder);
        if (startsWithInflectionsMatcher.find()) {
            inflections = inflectionsFieldFromSowpodsEntryParser.parse(entryWord, startsWithInflectionsMatcher.group(1));
            remainder = remainder.substring(startsWithInflectionsMatcher.end());
        }

        Matcher startsWithSpacesMatcher = startsWithSpaces.matcher(remainder);
        if (startsWithSpacesMatcher.find()) {
            remainder = remainder.substring(startsWithSpacesMatcher.end());
        }

        Matcher endsWithWhitespaceMatcher = endsWithWhitespace.matcher(remainder);
        if (endsWithWhitespaceMatcher.find()) {
            remainder = remainder.substring(0, endsWithWhitespaceMatcher.start());
        }

        return entryFactory.create(entryWord, partOfSpeech, inflections, remainder);
    }
}
