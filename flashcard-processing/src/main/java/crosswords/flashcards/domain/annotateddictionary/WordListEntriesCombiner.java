package crosswords.flashcards.domain.annotateddictionary;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.factories.AnnotatedEntryFactory;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.factories.bindingannotations.*;
import crosswords.flashcards.io.Ospd5FileToDomainObjectMapper;
import crosswords.flashcards.io.WordListFileToStringSetMapper;
import crosswords.flashcards.io.WordListFileToStringSetMapperInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by tim on 8/25/14.
 */
public class WordListEntriesCombiner {
    private final Ospd5FileToDomainObjectMapper ospd5FileToDomainObjectMapper;
    private final AnnotatedEntryFactory annotatedEntryFactory;
    private final EntryFactory entryFactory;
    private final Set<String> enable1;
    private final Set<String> wwf4;
    private final Set<String> otcwl2;
    private final Set<String> otcwl2014_2LW;
    private final Set<String> otcwl2014_some3LW;

    @Inject
    public WordListEntriesCombiner(Ospd5FileToDomainObjectMapper ospd5FileToDomainObjectMapper,
                                   AnnotatedEntryFactory annotatedEntryFactory,
                                   EntryFactory entryFactory,
                                   @Enable1 Set<String> enable1,
                                   @WordsWithFriends4 Set<String> wwf4,
                                   @Otcwl2 Set<String> otcwl2,
                                   @Otcwl2014TwoLetterWords Set<String> otcwl2014_2LW,
                                   @Otcwl2014ThreeFromTwo Set<String> otcwl2014_some3LW) {
        this.ospd5FileToDomainObjectMapper = ospd5FileToDomainObjectMapper;
        this.annotatedEntryFactory = annotatedEntryFactory;
        this.entryFactory = entryFactory;
        this.enable1 = enable1;
        this.wwf4 = wwf4;
        this.otcwl2 = otcwl2;
        this.otcwl2014_2LW = otcwl2014_2LW;
        this.otcwl2014_some3LW = otcwl2014_some3LW;
    }

    public void loadFilesAndInvokeCombination() {
        String projectRoot = "../";
        Map<String, Entry> ospd5EntryWordOrInflectionToEntry =
                ospd5FileToDomainObjectMapper.getOspd5EntryWordOrInflectionToEntry(projectRoot + "/dictionaries/sowpods_with_my_edits.txt");
        SortedSet<String> union = new TreeSet<String>(enable1);
        union.addAll(wwf4);
        union.addAll(otcwl2);
        union.addAll(otcwl2014_2LW);
        union.addAll(otcwl2014_some3LW);

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter   = new BufferedWriter(new FileWriter(projectRoot + "/dictionaries/union_non_ospd5.txt"));

            int i = 0;
            for (String word : union) {
                if (word.length() > 15) continue;
                Entry entry = ospd5EntryWordOrInflectionToEntry.get(word);
                if (entry == null) {
                    entry = entryFactory.create(word, null, null, null);
                }
                String toPrint;
                if (word.equals(entry.getEntryWord())) {
                    toPrint = annotatedEntryFactory.createForEntryWord(entry).formatForPrinting();
                } else {
                    toPrint = annotatedEntryFactory.createForInflection(word, entry).formatForPrinting();
                }
                bufferedWriter.write(toPrint + "\n");
                if (++i % 10000 == 0) {
                    System.out.println("Processed " + i + " records.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bufferedWriter != null) bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
