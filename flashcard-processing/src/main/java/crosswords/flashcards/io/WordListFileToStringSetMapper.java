package crosswords.flashcards.io;

import com.google.inject.Inject;
import crosswords.flashcards.factories.BufferedReaderFactory;
import crosswords.flashcards.factories.FileReaderFactory;
import crosswords.flashcards.factories.StringSortedSetFactory;
import crosswords.flashcards.factories.bindingannotations.Filename;

import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by tim on 8/25/14.
 */
public class WordListFileToStringSetMapper implements Provider<Set<String>> {
    private final StringSortedSetFactory stringSortedSetFactory;
    private final BufferedReaderFactory bufferedReaderFactory;
    private final FileReaderFactory fileReaderFactory;
    private final String fileName;
    private Set<String> output = new HashSet<String>();

    @Inject
    public WordListFileToStringSetMapper(StringSortedSetFactory stringSortedSetFactory,
                                         BufferedReaderFactory bufferedReaderFactory,
                                         FileReaderFactory fileReaderFactory,
                                         @Filename String fileName) {
        this.stringSortedSetFactory = stringSortedSetFactory;
        this.bufferedReaderFactory = bufferedReaderFactory;
        this.fileReaderFactory = fileReaderFactory;
        this.fileName = fileName;
    }

    public SortedSet<String> getSortedStringSet(String fileName) {
        SortedSet<String> wordList = stringSortedSetFactory.create();
        return fillSet(fileName, wordList);
    }

    public Set<String> getPerformantSet(String fileName) {
        return fillSet(fileName, output);
    }

    private <T extends Set<String>> T fillSet(String fileName, T wordList) {
        System.out.println("Filling set: " + fileName);
        BufferedReader wordListReader = null;
        try {
            String currentLine;

            wordListReader = bufferedReaderFactory.create(fileReaderFactory.create(fileName));

            while ((currentLine = wordListReader.readLine()) != null) {
                wordList.add(currentLine.toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (wordListReader != null) wordListReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return wordList;
    }

    @Override
    public Set<String> get() {
        // because this Provider is bound in singleton scope, it must be thread-safe:
        synchronized (output) {
            if (output.isEmpty()) {
                output = getPerformantSet(fileName);
            }
            return output;
        }
    }

}
