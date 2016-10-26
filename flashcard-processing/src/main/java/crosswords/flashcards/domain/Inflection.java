package crosswords.flashcards.domain;

public interface Inflection {
    String getEntryWord();

    String getCompleteInflection();

    InflectionCategory getCategory();

    boolean wasSpecifiedWithSuffix();

    String getDisplayString();
}
