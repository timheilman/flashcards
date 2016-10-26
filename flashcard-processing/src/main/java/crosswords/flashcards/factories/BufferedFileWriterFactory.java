package crosswords.flashcards.factories;

import crosswords.flashcards.factories.bindingannotations.OutputFileName;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedFileWriterFactory implements Provider<BufferedWriter> {

    private final String filename;

    @Inject
    public BufferedFileWriterFactory(@OutputFileName String filename) {
        this.filename = filename;
    }

    public BufferedWriter get() {
        try {
            return new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
