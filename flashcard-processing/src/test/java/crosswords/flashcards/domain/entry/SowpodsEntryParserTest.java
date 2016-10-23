package crosswords.flashcards.domain.entry;

import crosswords.flashcards.domain.Entry;
import crosswords.flashcards.domain.PartOfSpeech;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.factories.EntryFactory;
import crosswords.flashcards.domain.inflections.InflectionsFieldFromSowpodsEntryParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SowpodsEntryParserTest {
    private SowpodsEntryParser sowpodsEntryParserSut;
    private static final String ENTRY_STRING_DUMMY =
            "PYGMY\t _n_ *PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS* something that is a very small example of its type 9";
    private static final String ENTRY_STRING_DUMMY_WITH_NEWLINE =
            "PYGMY\t _n_ *PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS* something that is a very small example of its type 9\n";

    @Mock
    private InflectionsFieldFromSowpodsEntryParser inflectionsFieldFromSowpodsEntryParserMock;
    @Mock
    private PartOfSpeechParser partOfSpeechParserMock;
    @Mock
    private EntryFactory entryFactoryMock;
    @Mock
    private Entry expectedEntryDummy;
    @Mock
    private Inflections inflectionsDummy;

    @Before
    public void init() {
        initMocks(this);
        when(partOfSpeechParserMock.parse(anyString())).thenReturn(PartOfSpeech.NOUN);
        when(inflectionsFieldFromSowpodsEntryParserMock.parse(anyString(), anyString()))
                .thenReturn(inflectionsDummy);
        when(entryFactoryMock.create(anyString(), any(PartOfSpeech.class), any(Inflections.class), anyString()))
                .thenReturn(expectedEntryDummy);
        sowpodsEntryParserSut = new SowpodsEntryParser(entryFactoryMock, inflectionsFieldFromSowpodsEntryParserMock, partOfSpeechParserMock);
    }

    @Test
    public void testHappyPath() {
        Entry actualEntry = sowpodsEntryParserSut.parse(ENTRY_STRING_DUMMY);
        doVerifies(actualEntry);
    }

    @Test
    public void testWithTrailingNewline() {
        Entry actualEntry = sowpodsEntryParserSut.parse(ENTRY_STRING_DUMMY_WITH_NEWLINE);
        doVerifies(actualEntry);
    }

    private void doVerifies(Entry actualEntry) {
        verify(partOfSpeechParserMock).parse("n");
        verify(inflectionsFieldFromSowpodsEntryParserMock).parse("PYGMY", "*PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS*");
        verify(entryFactoryMock).create("PYGMY", PartOfSpeech.NOUN, inflectionsDummy, "something that is a very small example of its type 9");
        assertEquals(expectedEntryDummy, actualEntry);
    }

    // todo: test with newline
}
