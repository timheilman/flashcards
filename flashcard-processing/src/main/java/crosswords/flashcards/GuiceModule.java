package crosswords.flashcards;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import crosswords.flashcards.domain.*;
import crosswords.flashcards.domain.entry.impl.AnnotatedEntryImpl;
import crosswords.flashcards.domain.entry.impl.AnnotatedInflectionEntryImpl;
import crosswords.flashcards.domain.entry.impl.EntryImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionFromSuffixImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionImpl;
import crosswords.flashcards.domain.inflections.impl.InflectionsImpl;
import crosswords.flashcards.factories.*;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
                .implement(new TypeLiteral<Map<String, Entry>>(){}, new TypeLiteral<HashMap<String, Entry>>(){})
                .build(DictionaryFactory.class));
        install(factoryModuleBuilder
                .implement(new TypeLiteral<SortedSet<String>>(){}, new TypeLiteral<TreeSet<String>>(){})
                .build(StringSortedSetFactory.class));
    }
}
