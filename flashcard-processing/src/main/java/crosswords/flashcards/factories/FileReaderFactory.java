package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by tim on 8/25/14.
 */
public class FileReaderFactory {
    public FileReader create(String filename) throws FileNotFoundException {
        return new FileReader(filename);
    }
}
