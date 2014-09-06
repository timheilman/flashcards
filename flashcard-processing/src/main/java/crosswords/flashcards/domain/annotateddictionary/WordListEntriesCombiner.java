package crosswords.flashcards.domain.annotateddictionary;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.factories.AnnotatedEntryFactory;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.factories.bindingannotations.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by tim on 8/25/14.
 */
public class WordListEntriesCombiner {
    private final AnnotatedEntryFactory annotatedEntryFactory;
    private final EntryFactory entryFactory;
    private final Map<String, Entry> sowpodsEntryWordOrInflectionToEntry;
    private final Set<String> unionNonSowpods;
    private final Writer writer;

    @Inject
    public WordListEntriesCombiner(Map<String, Entry> sowpodsEntryWordOrInflectionToEntry,
                                   AnnotatedEntryFactory annotatedEntryFactory,
                                   EntryFactory entryFactory,
                                   @UnionNonSowpods SortedSet<String> unionNonSowpods,
                                   Writer writer) {
        this.sowpodsEntryWordOrInflectionToEntry = sowpodsEntryWordOrInflectionToEntry;
        this.annotatedEntryFactory = annotatedEntryFactory;
        this.entryFactory = entryFactory;
        this.unionNonSowpods = unionNonSowpods;
        this.writer = writer;
    }

    public void loadFilesAndInvokeCombination() {
        try {
            int i = 0;
            for (String word : unionNonSowpods) {
                if (word.length() > 15) continue;
                Entry entry = sowpodsEntryWordOrInflectionToEntry.get(word);
                if (entry == null) {
                    entry = entryFactory.create(word, null, null, null);
                }
                String toPrint;
                if (word.equals(entry.getEntryWord())) {
                    toPrint = annotatedEntryFactory.createForEntryWord(entry).formatForPrinting();
                } else {
                    toPrint = annotatedEntryFactory.createForInflection(word, entry).formatForPrinting();
                }
                writer.write(toPrint + "\n");
                if (++i % 10000 == 0) {
                    System.out.println("Processed " + i + " records.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
