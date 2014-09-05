package crosswords.flashcards.io;

import com.google.inject.Inject;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.domain.entry.Ospd5EntryParser;
import crosswords.flashcards.factories.BufferedReaderFactory;
import crosswords.flashcards.factories.DictionaryFactory;
import crosswords.flashcards.factories.FileReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tim on 8/25/14.
 */
public class Ospd5FileToDomainObjectMapper {

    private final Ospd5EntryParser ospd5EntryParser;
    private final BufferedReaderFactory bufferedReaderFactory;
    private final FileReaderFactory fileReaderFactory;
    private final DictionaryFactory dictionaryFactory;

    @Inject
    public Ospd5FileToDomainObjectMapper(Ospd5EntryParser ospd5EntryParser, BufferedReaderFactory bufferedReaderFactory, FileReaderFactory fileReaderFactory, DictionaryFactory dictionaryFactory) {
        this.ospd5EntryParser = ospd5EntryParser;
        this.bufferedReaderFactory = bufferedReaderFactory;
        this.fileReaderFactory = fileReaderFactory;
        this.dictionaryFactory = dictionaryFactory;
    }

    public Map<String, Entry> getOspd5EntryWordOrInflectionToEntry(String fileName) {
        Map<String, Entry> ospd5EntryWordOrInflectionToEntry = dictionaryFactory.createDictionary();
        BufferedReader ospd5reader = null;
        try {
            String currentLine;

            ospd5reader = bufferedReaderFactory.create(fileReaderFactory.create(fileName));

            while ((currentLine = ospd5reader.readLine()) != null) {
                Entry entry = ospd5EntryParser.parse(currentLine);
                // we want to prefer an inflection definition!
                if (!ospd5EntryWordOrInflectionToEntry.containsKey(entry.getEntryWord())) {
                    ospd5EntryWordOrInflectionToEntry.put(entry.getEntryWord(), entry);
                }
                Inflections inflections = entry.getInflections();
                if (inflections != null) {
                    for (Inflection inflection : inflections) {
                        ospd5EntryWordOrInflectionToEntry.put(inflection.getCompleteInflection(), entry);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ospd5reader != null) ospd5reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ospd5EntryWordOrInflectionToEntry;
    }
}
