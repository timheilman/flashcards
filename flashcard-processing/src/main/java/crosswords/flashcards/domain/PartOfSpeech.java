package crosswords.flashcards.domain;

public enum PartOfSpeech {
    ADJECTIVE("adj"),
    ADVERB("adv"),
    CONJUNCTION("conj"),
    DETERMINER("determiner"),
    INTERJECTION_AND_NOUN("interj, n"),
    INTERJECTION("interj"),
    NOUN_AND_VERB("n, vb"),
    NOUN("n"),
    PLURAL_NOUN("pl n"),
    PREPOSITION("prep"),
    PRONOUN("pron"),
    SENTENCE_SUBSTITUTE("sentence substitute"),
    VERB("vb");

    private final String displayString;

    PartOfSpeech(String displayString) {
        this.displayString = displayString;
    }

    public String getDisplayString() {
        return displayString;
    }
}
