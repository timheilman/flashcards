package crosswords.flashcards.factories;

import com.google.inject.assistedinject.Assisted;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileReaderFactory {
    public FileReader create(String filename) throws FileNotFoundException {
        return new FileReader(filename);
    }
}
