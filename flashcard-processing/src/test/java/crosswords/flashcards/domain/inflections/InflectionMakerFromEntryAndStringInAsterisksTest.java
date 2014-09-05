package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionFromSuffix;
import crosswords.flashcards.factories.InflectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by tim on 8/23/14.
 */
public class InflectionMakerFromEntryAndStringInAsterisksTest {
    public static final String ENTRY_WORD_DUMMY = "PYGMY";
    public static final String SUFFIX_DUMMY_WITH_HYPHEN_DUMMY = "-ISM";
    public static final String SUFFIX_DUMMY_WITHOUT_HYPHEN_DUMMY = "ISM";
    public static final String ENTRY_WORD_WITH_SUFFIX_DUMMY = "PYGMYISM";
    public static final String COMPLETE_DUMMY = "PYGMAEAN";
    private InflectionMakerFromEntryAndStringInAsterisks inflectionMakerFromEntryAndStringInAsterisksSut;

    @Mock
    private InflectionFactory inflectionFactorySpy;
    @Mock
    private InflectionFromSuffix expectedSuffixInflectionDummy;
    @Mock
    private Inflection expectedCompleteInflectionDummy;

    @Before
    public void init() {
        initMocks(this);

        when(inflectionFactorySpy.create(anyString(), anyString(), anyString())).thenReturn(expectedSuffixInflectionDummy);
        when(inflectionFactorySpy.create(anyString(), anyString())).thenReturn(expectedCompleteInflectionDummy);
        inflectionMakerFromEntryAndStringInAsterisksSut = new InflectionMakerFromEntryAndStringInAsterisks(inflectionFactorySpy);
    }

    @Test
    public void testSuffixInflection() throws InterruptedException {
        Inflection actualInflection = inflectionMakerFromEntryAndStringInAsterisksSut.get(ENTRY_WORD_DUMMY, SUFFIX_DUMMY_WITH_HYPHEN_DUMMY);

        verify(inflectionFactorySpy).create(ENTRY_WORD_DUMMY, SUFFIX_DUMMY_WITHOUT_HYPHEN_DUMMY, ENTRY_WORD_WITH_SUFFIX_DUMMY);
        verifyNoMoreInteractions(inflectionFactorySpy);
        assertEquals(expectedSuffixInflectionDummy, actualInflection);
    }

    @Test
    public void testCompleteInflection() {
        Inflection actualInflection = inflectionMakerFromEntryAndStringInAsterisksSut.get(ENTRY_WORD_DUMMY, COMPLETE_DUMMY);
        verify(inflectionFactorySpy).create(ENTRY_WORD_DUMMY, COMPLETE_DUMMY);
        verifyNoMoreInteractions(inflectionFactorySpy);
        assertEquals(expectedCompleteInflectionDummy, actualInflection);
    }

    @Test
    public void testImaginedHyphenEdgeCase() {
        try {
            Inflection actualInflection = inflectionMakerFromEntryAndStringInAsterisksSut.get(ENTRY_WORD_DUMMY, "-");
        } catch (Throwable t) {
            return;
        }
        fail("Expected exception");
    }
}
