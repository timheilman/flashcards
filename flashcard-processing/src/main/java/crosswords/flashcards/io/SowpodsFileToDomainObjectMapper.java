package crosswords.flashcards.io;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.domain.entry.SowpodsEntryParser;
import crosswords.flashcards.factories.BufferedReaderFactory;
import crosswords.flashcards.factories.DictionaryFactory;
import crosswords.flashcards.factories.FileReaderFactory;
import crosswords.flashcards.factories.bindingannotations.SowpodsDictionaryFileName;

import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tim on 8/25/14.
 */
public class SowpodsFileToDomainObjectMapper implements Provider<Map<String, Entry>> {

    private final SowpodsEntryParser sowpodsEntryParser;
    private final BufferedReaderFactory bufferedReaderFactory;
    private final FileReaderFactory fileReaderFactory;
    private final DictionaryFactory dictionaryFactory;
    private final String fileName;

    @Inject
    public SowpodsFileToDomainObjectMapper(SowpodsEntryParser sowpodsEntryParser,
                                           BufferedReaderFactory bufferedReaderFactory,
                                           FileReaderFactory fileReaderFactory,
                                           DictionaryFactory dictionaryFactory,
                                           @SowpodsDictionaryFileName String fileName) {
        this.sowpodsEntryParser = sowpodsEntryParser;
        this.bufferedReaderFactory = bufferedReaderFactory;
        this.fileReaderFactory = fileReaderFactory;
        this.dictionaryFactory = dictionaryFactory;
        this.fileName = fileName;
    }

    public Map<String, Entry> get() {
        Map<String, Entry> sowpodsEntryWordOrInflectionToEntry = dictionaryFactory.createDictionary();
        BufferedReader sowpodsReader = null;
        try {
            String currentLine;

            sowpodsReader = bufferedReaderFactory.create(fileReaderFactory.create(fileName));

            while ((currentLine = sowpodsReader.readLine()) != null) {
                Entry entry = sowpodsEntryParser.parse(currentLine);
                // we want to prefer an inflection definition!
                if (!sowpodsEntryWordOrInflectionToEntry.containsKey(entry.getEntryWord())) {
                    sowpodsEntryWordOrInflectionToEntry.put(entry.getEntryWord(), entry);
                }
                Inflections inflections = entry.getInflections();
                if (inflections != null) {
                    for (Inflection inflection : inflections) {
                        sowpodsEntryWordOrInflectionToEntry.put(inflection.getCompleteInflection(), entry);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (sowpodsReader != null) sowpodsReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sowpodsEntryWordOrInflectionToEntry;
    }
}
