package crosswords.flashcards.domain.entry;

import crosswords.flashcards.domain.PartOfSpeech;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

public class PartOfSpeechParserTest {
    private PartOfSpeechParser partOfSpeechParserSut;
    private static final String PART_OF_SPEECH_STRING_DUMMY = "vb";

    @Before
    public void init() {
        partOfSpeechParserSut = new PartOfSpeechParser();
    }

    @Test
    public void testHappyPath() {
        PartOfSpeech actualPartOfSpeech = partOfSpeechParserSut.parse(PART_OF_SPEECH_STRING_DUMMY);
        assertEquals(PartOfSpeech.VERB, (actualPartOfSpeech));
    }

}
