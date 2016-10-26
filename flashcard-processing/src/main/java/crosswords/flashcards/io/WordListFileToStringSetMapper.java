package crosswords.flashcards.io;

import com.google.inject.Inject;
import crosswords.flashcards.factories.BufferedReaderFactory;
import crosswords.flashcards.factories.FileReaderFactory;
import crosswords.flashcards.factories.bindingannotations.WordListFileName;

import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WordListFileToStringSetMapper implements Provider<Set<String>> {
    private final BufferedReaderFactory bufferedReaderFactory;
    private final FileReaderFactory fileReaderFactory;
    private final String fileName;
    private Set<String> output = new HashSet<String>();

    @Inject
    public WordListFileToStringSetMapper(BufferedReaderFactory bufferedReaderFactory,
                                         FileReaderFactory fileReaderFactory,
                                         @WordListFileName String fileName) {
        this.bufferedReaderFactory = bufferedReaderFactory;
        this.fileReaderFactory = fileReaderFactory;
        this.fileName = fileName;
    }

    private Set<String> getPerformantSet(String fileName) {
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
