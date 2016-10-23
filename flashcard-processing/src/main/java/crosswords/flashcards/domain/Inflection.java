package crosswords.flashcards.domain;

public interface Inflection {
    public String getEntryWord();
    public String getCompleteInflection();
    public InflectionCategory getCategory();
    public boolean wasSpecifiedWithSuffix();

    String getDisplayString();
}
