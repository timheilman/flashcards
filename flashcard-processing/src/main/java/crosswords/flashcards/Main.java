package crosswords.flashcards;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.annotateddictionary.WordListEntriesCombiner;
import crosswords.flashcards.factories.bindingannotations.Enable1;
import crosswords.flashcards.factories.bindingannotations.Filename;
import crosswords.flashcards.io.Ospd5FileToDomainObjectMapper;
import crosswords.flashcards.io.WordListFileToStringSetMapper;

import java.io.*;
import java.util.*;

/**
 * Created by tim on 8/24/14.
 */
public class Main {
    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new GuiceModule());

        WordListEntriesCombiner wordListEntriesCombiner = injector.getInstance(WordListEntriesCombiner.class);
        wordListEntriesCombiner.loadFilesAndInvokeCombination();
    }
}
