package crosswords.flashcards.domain;

public interface Entry {
    String getEntryWord();
    PartOfSpeech getPartOfSpeech();
    Inflections getInflections();
    String getDefinition();
}
