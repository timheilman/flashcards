package crosswords.flashcards;

import com.google.inject.Guice;
import com.google.inject.Injector;
import crosswords.flashcards.domain.annotateddictionary.WordListEntriesCombiner;

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
