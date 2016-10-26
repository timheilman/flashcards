package crosswords.flashcards.factories;


import java.io.BufferedReader;
import java.io.Reader;

public class BufferedReaderFactory {
    public BufferedReader create(Reader reader) {
        return new BufferedReader(reader);
    }
}
