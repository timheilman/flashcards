package crosswords.flashcards;

import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import crosswords.flashcards.io.WordListFileToStringSetMapper;

import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.util.Set;

public abstract class WordListFileToStringSetMapperModule extends PrivateModule {
    private final Class<? extends Annotation> annotation;

    public WordListFileToStringSetMapperModule(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override
    protected void configure() {
        TypeLiteral<Set<String>> typeLiteral = new TypeLiteral<Set<String>>() {
        };
        bind(typeLiteral).annotatedWith(annotation).toProvider(WordListFileToStringSetMapper.class).in(Singleton.class);
        expose(typeLiteral).annotatedWith(annotation);

        bindFileName();
    }

    abstract void bindFileName();
}
