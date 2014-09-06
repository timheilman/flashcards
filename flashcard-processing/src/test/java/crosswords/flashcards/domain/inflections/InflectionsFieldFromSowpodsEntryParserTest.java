package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.factories.InflectionsFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by tim on 8/23/14.
 */
public class InflectionsFieldFromSowpodsEntryParserTest {

    public static final String ENTRY_WORD_DUMMY = "PYGMY";
    public static final String INFLECTIONS_FIELD_DUMMY = "*PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS*";
    public static final String INFLECTIONS_FIELD_DUMMY_LEADTRAIL_WHITESPACE = " *PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS* ";

    @Mock
    private InflectionMakerFromEntryAndStringInAsterisks inflectionMakerFromEntryAndStringInAsterisksMock;
    @Mock
    private InflectionsFactory inflectionsFactoryMock;
    @Mock
    private Inflections inflectionsMock;
    private InflectionsFieldFromSowpodsEntryParser inflectionsFieldFromSowpodsEntryParserSut;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(inflectionsFactoryMock.create()).thenReturn(inflectionsMock);
        inflectionsFieldFromSowpodsEntryParserSut = new InflectionsFieldFromSowpodsEntryParser(inflectionMakerFromEntryAndStringInAsterisksMock, inflectionsFactoryMock);
    }

    @Test
    public void testParserHappyPath() {
        // INITIALIZATION
        List<Inflection> expectedInflectionDummies = makeExpectedInflectionDummies();

        // INVOKE SUT
        Inflections actualInflectionsNoWhitespace = inflectionsFieldFromSowpodsEntryParserSut.parse(ENTRY_WORD_DUMMY, INFLECTIONS_FIELD_DUMMY);

        // VERIFICATION
        verifyStuff(expectedInflectionDummies, actualInflectionsNoWhitespace);
    }

    @Test
    public void testParserWithLeadTrailWhitespace() {
        // INITIALIZATION
        List<Inflection> expectedInflectionDummies = makeExpectedInflectionDummies();

        // INVOKE SUT
        Inflections actualInflectionsWithWhitespace = inflectionsFieldFromSowpodsEntryParserSut.parse(ENTRY_WORD_DUMMY, INFLECTIONS_FIELD_DUMMY_LEADTRAIL_WHITESPACE);

        // VERIFICATION
        verifyStuff(expectedInflectionDummies, actualInflectionsWithWhitespace);
    }

    private void verifyStuff(List<Inflection> expectedInflectionDummies, Inflections actualInflectionsNoWhitespace) {
        assertEquals(inflectionsMock, actualInflectionsNoWhitespace);
        for (Inflection expectedInflectionDummy : expectedInflectionDummies) {
            verify(inflectionsMock).add(expectedInflectionDummy);
        }
        verifyNoMoreInteractions(inflectionsMock);
        verify(inflectionMakerFromEntryAndStringInAsterisksMock, times(7)).get(anyString(), anyString());
    }

    private List<Inflection> makeExpectedInflectionDummies() {
        List<Inflection> expectedInflectionDummies = new ArrayList<Inflection>();
        for (int i = 0; i<7; ++i) {
            Inflection inflectionDummy = mock(Inflection.class);
            expectedInflectionDummies.add(inflectionDummy);
        }
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "PIGMY")).thenReturn(expectedInflectionDummies.get(0));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "PYGMAEAN")).thenReturn(expectedInflectionDummies.get(1));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "PYGMEAN")).thenReturn(expectedInflectionDummies.get(2));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "PYGMIES")).thenReturn(expectedInflectionDummies.get(3));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "-ISH")).thenReturn(expectedInflectionDummies.get(4));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "-ISM")).thenReturn(expectedInflectionDummies.get(5));
        when(inflectionMakerFromEntryAndStringInAsterisksMock.get(ENTRY_WORD_DUMMY, "-ISMS")).thenReturn(expectedInflectionDummies.get(6));
        return expectedInflectionDummies;
    }
}
