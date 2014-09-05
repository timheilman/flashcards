package crosswords.flashcards.domain.annotateddictionary;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.factories.AnnotatedEntryFactory;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.io.Ospd5FileToDomainObjectMapper;
import crosswords.flashcards.io.WordListFileToStringSetMapper;

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
    private final WordListFileToStringSetMapper wordListFileToStringSetMapper;
    private final AnnotatedEntryFactory annotatedEntryFactory;
    private final EntryFactory entryFactory;

    @Inject
    public WordListEntriesCombiner(Ospd5FileToDomainObjectMapper ospd5FileToDomainObjectMapper,
                                   WordListFileToStringSetMapper wordListFileToStringSetMapper,
                                   AnnotatedEntryFactory annotatedEntryFactory,
                                   EntryFactory entryFactory) {
        this.ospd5FileToDomainObjectMapper = ospd5FileToDomainObjectMapper;
        this.wordListFileToStringSetMapper = wordListFileToStringSetMapper;
        this.annotatedEntryFactory = annotatedEntryFactory;
        this.entryFactory = entryFactory;
    }

    public void loadFilesAndInvokeCombination() {
        String projectRoot = "../";
        Map<String, Entry> ospd5EntryWordOrInflectionToEntry =
                ospd5FileToDomainObjectMapper.getOspd5EntryWordOrInflectionToEntry(projectRoot + "/dictionaries/sowpods_with_my_edits.txt");
        Set<String> enable1 = wordListFileToStringSetMapper.getPerformantSet(projectRoot + "/wordlists/enable1.txt");
        Set<String> wwf4 = wordListFileToStringSetMapper.getPerformantSet(projectRoot + "/wordlists/greenworm_dot_net_wwf_v4point0.txt");
        Set<String> otcwl2 = wordListFileToStringSetMapper.getPerformantSet(projectRoot + "/wordlists/otcwl2.txt");
        Set<String> otcwl2014_2LW = wordListFileToStringSetMapper.getPerformantSet(projectRoot + "/wordlists/otcwl2014_2LW.txt");
        Set<String> otcwl2014_some3LW = wordListFileToStringSetMapper.getPerformantSet(projectRoot + "/wordlists/otcwl2014_some3LW.txt");
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
                    toPrint = annotatedEntryFactory.createForEntryWord(enable1, wwf4, otcwl2, otcwl2014_2LW, otcwl2014_some3LW, entry).formatForPrinting();
                } else {
                    toPrint = annotatedEntryFactory.createForInflection(enable1, wwf4, otcwl2, otcwl2014_2LW, otcwl2014_some3LW, word, entry).formatForPrinting();
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
