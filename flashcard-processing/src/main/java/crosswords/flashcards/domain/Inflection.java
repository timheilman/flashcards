package crosswords.flashcards.domain;

/**
 * Created by tim on 8/23/14.
 */
public interface Inflection {
    public String getEntryWord();
    public String getCompleteInflection();
    public InflectionCategory getCategory();
    public boolean wasSpecifiedWithSuffix();

    String getDisplayString();
}
