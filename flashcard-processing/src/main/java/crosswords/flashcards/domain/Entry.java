package crosswords.flashcards.domain;

/**
 * Created by tim on 8/24/14.
 */
public interface Entry {
    String getEntryWord();
    PartOfSpeech getPartOfSpeech();
    Inflections getInflections();
    String getDefinition();
}
