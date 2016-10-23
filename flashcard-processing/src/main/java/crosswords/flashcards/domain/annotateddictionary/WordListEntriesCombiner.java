package crosswords.flashcards.domain.annotateddictionary;

import com.google.inject.Inject;
import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.factories.AnnotatedEntryFactory;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.factories.bindingannotations.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

public class WordListEntriesCombiner implements Runnable {
    private final AnnotatedEntryFactory annotatedEntryFactory;
    private final EntryFactory entryFactory;
    private final Map<String, Entry> sowpodsEntryWordOrInflectionToEntry;
    private final Iterator<String> nonThreadSafeIteratorForUnionNonSowpods;
    private final SortedMap<String, AnnotatedEntry> outputDictionary;
    private static final AtomicInteger recordsProcessed = new AtomicInteger(0);

    @Inject
    public WordListEntriesCombiner(Map<String, Entry> sowpodsEntryWordOrInflectionToEntry,
                                   AnnotatedEntryFactory annotatedEntryFactory,
                                   EntryFactory entryFactory,
                                   @UnionNonSowpods Iterator<String> nonThreadSafeIteratorForUnionNonSowpods,
                                   @UnionNonSowpods SortedMap<String, AnnotatedEntry> outputDictionary) {
        this.sowpodsEntryWordOrInflectionToEntry = sowpodsEntryWordOrInflectionToEntry;
        this.annotatedEntryFactory = annotatedEntryFactory;
        this.entryFactory = entryFactory;
        this.nonThreadSafeIteratorForUnionNonSowpods = nonThreadSafeIteratorForUnionNonSowpods;
        this.outputDictionary = outputDictionary;
    }

    public void pullFromIteratorAndOutputToTreeSet() {
        while (true) {
            String word;
            synchronized (nonThreadSafeIteratorForUnionNonSowpods) {
                if (!nonThreadSafeIteratorForUnionNonSowpods.hasNext()) {
                    return;
                }
                word = nonThreadSafeIteratorForUnionNonSowpods.next();
                if (recordsProcessed.addAndGet(1) % 10000 == 0) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " has processed the " + recordsProcessed.get() + "th record.");
                }
            }
            if (word.length() > 15) continue;
            Entry entry = sowpodsEntryWordOrInflectionToEntry.get(word);
            if (entry == null) {
                entry = entryFactory.create(word, null, null, null);
            }
            AnnotatedEntry annotatedEntry;
            if (word.equals(entry.getEntryWord())) {
                annotatedEntry = annotatedEntryFactory.createForEntryWord(entry);
            } else {
                annotatedEntry = annotatedEntryFactory.createForInflection(word, entry);
            }
            synchronized (nonThreadSafeIteratorForUnionNonSowpods) {
                outputDictionary.put(word, annotatedEntry);
            }
        }
    }

    @Override
    public void run() {
        pullFromIteratorAndOutputToTreeSet();
    }
}
