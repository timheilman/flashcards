package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class BufferedReaderFactory {
    public BufferedReader create(Reader reader) {
        return new BufferedReader(reader);
    }
}
