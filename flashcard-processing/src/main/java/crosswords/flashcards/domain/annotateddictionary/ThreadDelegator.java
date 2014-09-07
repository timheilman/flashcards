package crosswords.flashcards.domain.annotateddictionary;

import crosswords.flashcards.domain.AnnotatedEntry;
import crosswords.flashcards.factories.WordListEntriesCombinerFactory;
import crosswords.flashcards.factories.bindingannotations.UnionNonSowpods;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tim on 9/7/14.
 */
public class ThreadDelegator {
    public static final int THREAD_POOL_SIZE = 8;
    private final Writer writer;
    private final ExecutorService executorService;
    private final WordListEntriesCombinerFactory wordListEntriesCombinerFactory;
    private final SortedMap<String, AnnotatedEntry> outputDictionary;

    @Inject
    public ThreadDelegator(Writer writer,
                           ExecutorService executorService,
                           WordListEntriesCombinerFactory wordListEntriesCombinerFactory,
                           @UnionNonSowpods SortedMap<String, AnnotatedEntry> outputDictionary) {
        this.writer = writer;
        this.executorService = executorService;
        this.wordListEntriesCombinerFactory = wordListEntriesCombinerFactory;
        this.outputDictionary = outputDictionary;
    }


    public void delegate() {
        for (int i = 0; i < THREAD_POOL_SIZE; ++i) {
            executorService.execute(wordListEntriesCombinerFactory.create());
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            for (AnnotatedEntry annotatedEntry : outputDictionary.values()) {
                writer.write(annotatedEntry.formatForPrinting() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
