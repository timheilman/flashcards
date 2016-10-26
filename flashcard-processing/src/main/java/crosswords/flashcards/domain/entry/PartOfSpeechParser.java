package crosswords.flashcards.domain.entry;

import crosswords.flashcards.domain.PartOfSpeech;

import java.util.HashMap;

import static crosswords.flashcards.domain.PartOfSpeech.*;

class PartOfSpeechParser {
    private final HashMap<String, PartOfSpeech> mapping;

    PartOfSpeechParser() {
        mapping = new HashMap<String, PartOfSpeech>();
        mapping.put("adj", ADJECTIVE);
        mapping.put("adv", ADVERB);
        mapping.put("conj", CONJUNCTION);
        mapping.put("determiner", DETERMINER);
        mapping.put("interj, n", INTERJECTION_AND_NOUN);
        mapping.put("interj", INTERJECTION);
        mapping.put("n, vb", NOUN_AND_VERB);
        mapping.put("n", NOUN);
        mapping.put("noun", NOUN);
        mapping.put("pl n", PLURAL_NOUN);
        mapping.put("prep", PREPOSITION);
        mapping.put("pron", PRONOUN);
        mapping.put("sentence substitute", SENTENCE_SUBSTITUTE);
        mapping.put("v", VERB);
        mapping.put("vb", VERB);
    }

    PartOfSpeech parse(String entryPartOfSpeechString) {
        return mapping.get(entryPartOfSpeechString);
    }
}
