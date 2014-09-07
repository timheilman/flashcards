package crosswords.flashcards;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import crosswords.flashcards.domain.*;
import crosswords.flashcards.domain.annotateddictionary.ThreadDelegator;
import crosswords.flashcards.domain.annotateddictionary.WordListEntriesCombiner;
import crosswords.flashcards.domain.entry.impl.AnnotatedEntryImpl;
import crosswords.flashcards.domain.entry.impl.AnnotatedInflectionEntryImpl;
import crosswords.flashcards.domain.entry.impl.EntryImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionFromSuffixImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionsImpl;
import crosswords.flashcards.factories.*;
import crosswords.flashcards.factories.bindingannotations.*;
import crosswords.flashcards.io.SowpodsFileToDomainObjectMapper;
import crosswords.flashcards.io.WordlistUnionTaker;

import javax.inject.Singleton;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tim on 8/24/14.
 */
public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
        // Ask Guice to handle interface-only factories:
        install(factoryModuleBuilder
                .implement(Inflection.class, InflectionImpl.class)
                .implement(InflectionFromSuffix.class, InflectionFromSuffixImpl.class)
                .build(InflectionFactory.class));
        install(factoryModuleBuilder
                .implement(Inflections.class, InflectionsImpl.class)
                .build(InflectionsFactory.class));
        install(factoryModuleBuilder
                .implement(Entry.class, EntryImpl.class)
                .build(EntryFactory.class));
        install(factoryModuleBuilder
                .implement(AnnotatedEntry.class, AnnotatedEntryImpl.class)
                .implement(AnnotatedInflectionEntry.class, AnnotatedInflectionEntryImpl.class)
                .build(AnnotatedEntryFactory.class));
        install(factoryModuleBuilder
                .implement(new TypeLiteral<Map<String, Entry>>() {
                }, new TypeLiteral<HashMap<String, Entry>>() {
                })
                .build(DictionaryFactory.class));
        install(factoryModuleBuilder
                .implement(WordListEntriesCombiner.class, WordListEntriesCombiner.class)
                .build(WordListEntriesCombinerFactory.class));

        bind(new TypeLiteral<SortedMap<String, AnnotatedEntry>>(){}).annotatedWith(UnionNonSowpods.class).toInstance(new TreeMap<String, AnnotatedEntry>());
        bind(ExecutorService.class).toInstance(Executors.newFixedThreadPool(ThreadDelegator.THREAD_POOL_SIZE));

        installWordList(Enable1.class, "../wordlists/enable1.txt");
        installWordList(WordsWithFriends4.class, "../wordlists/greenworm_dot_net_wwf_v4point0.txt");
        installWordList(Otcwl2.class, "../wordlists/otcwl2.txt");
        installWordList(Otcwl2014TwoToFourLetterWords.class, "../wordlists/otcwl2014_2-4LW.txt");
        installUnionWordList();

        installDictionary("../dictionaries/sowpods_with_my_edits.txt");

        installOutputWriter("../dictionaries/union_non_sowpods.txt");

    }

    private void installOutputWriter(String filename) {
        bindConstant().annotatedWith(OutputFileName.class).to(filename);
        bind(Writer.class).toProvider(BufferedFileWriterFactory.class).in(Singleton.class);
    }

    private void installUnionWordList() {
        bind(new TypeLiteral<Iterator<String>>(){}).annotatedWith(UnionNonSowpods.class).toProvider(WordlistUnionTaker.class).in(Singleton.class);
    }

    private void installDictionary(String filename) {
        bindConstant().annotatedWith(SowpodsDictionaryFileName.class).to(filename);
        bind(new TypeLiteral<Map<String, Entry>>() {
        }).toProvider(SowpodsFileToDomainObjectMapper.class).in(Singleton.class);
    }

    private void installWordList(final Class<? extends Annotation> annotationClass, final String fileName) {
        install(new WordListFileToStringSetMapperModule(annotationClass) {
            @Override
            void bindFileName() {
                bindConstant().annotatedWith(WordListFileName.class).to(fileName);
            }
        });
    }
}
