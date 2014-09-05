package crosswords.flashcards.io;

import com.google.inject.Inject;
import crosswords.flashcards.factories.BufferedReaderFactory;
import crosswords.flashcards.factories.FileReaderFactory;
import crosswords.flashcards.factories.StringSortedSetFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by tim on 8/25/14.
 */
public class WordListFileToStringSetMapper {
    private final StringSortedSetFactory stringSortedSetFactory;
    private final BufferedReaderFactory bufferedReaderFactory;
    private final FileReaderFactory fileReaderFactory;

    @Inject
    public WordListFileToStringSetMapper(StringSortedSetFactory stringSortedSetFactory, BufferedReaderFactory bufferedReaderFactory, FileReaderFactory fileReaderFactory) {
        this.stringSortedSetFactory = stringSortedSetFactory;
        this.bufferedReaderFactory = bufferedReaderFactory;
        this.fileReaderFactory = fileReaderFactory;
    }

    public SortedSet<String> getSortedStringSet(String fileName) {
        SortedSet<String> wordList = stringSortedSetFactory.create();
        return fillSet(fileName, wordList);
    }

    public Set<String> getPerformantSet(String fileName) {
        Set<String> toReturn = new HashSet<String>();
        return fillSet(fileName, toReturn);
    }

    private <T extends Set<String>> T fillSet(String fileName, T wordList) {
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
}
