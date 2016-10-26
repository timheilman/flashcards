package crosswords.flashcards;

import com.google.inject.Guice;
import com.google.inject.Injector;
import crosswords.flashcards.domain.annotateddictionary.ThreadDelegator;

public class Main {
    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new GuiceModule());

        ThreadDelegator threadDelegator = injector.getInstance(ThreadDelegator.class);
        threadDelegator.delegate();
    }
}
