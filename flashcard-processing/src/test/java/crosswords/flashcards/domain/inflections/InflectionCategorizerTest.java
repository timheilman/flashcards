package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionCategory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by tim on 8/25/14.
 */
public class InflectionCategorizerTest {
    public static final String SUFFIX_DUMMY = "ISM";

//    *PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS*

    private InflectionCategorizer inflectionCategorizerSut;
    @Mock
    private Inflection inflectionStub;

    @Before
    public void init() {
        initMocks(this);
        when(inflectionStub.getEntryWord()).thenReturn("PYGMY");
        inflectionCategorizerSut = new InflectionCategorizer();
    }

    @Test
    public void testWithSuffix() {
        assertEquals(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN, inflectionCategorizerSut.categorize(SUFFIX_DUMMY));
    }

    @Test
    public void testWithoutSuffix() {
        initInflectionMockWithCompleteInflection("PYGMIES");
        assertEquals(InflectionCategory.PLURALIZATION_AND_CONJUGATION_WITH_S, inflectionCategorizerSut.categorize(inflectionStub));
    }

    private void initInflectionMockWithCompleteInflection(String completeInflection) {
        when(inflectionStub.getCompleteInflection()).thenReturn(completeInflection);
    }
}
