package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by tim on 8/25/14.
 */
public class BufferedReaderFactory {
    public BufferedReader create(Reader reader) {
        return new BufferedReader(reader);
    }
}
