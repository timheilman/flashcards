package crosswords.flashcards.domain.inflections;

import crosswords.flashcards.domain.Inflection;
import crosswords.flashcards.domain.InflectionCategory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by tim on 8/25/14.
 */
public class InflectionComparatorTest {

//    *PIGMY*, *PYGMAEAN*, *PYGMEAN*, *PYGMIES*, *-ISH*, *-ISM*, *-ISMS*

    private InflectionComparator inflectionComparatorSut;
    @Mock
    private Inflection inflectionStub;
    @Mock
    private Inflection inflectionStub2;

    @Before
    public void init() {
        initMocks(this);
        inflectionComparatorSut = new InflectionComparator();
    }

    @Test
    public void testOnCategoryOnlyForward() {
        when(inflectionStub.getCategory()).thenReturn(InflectionCategory.PLURALIZATION_AND_CONJUGATION_WITH_S);
        when(inflectionStub2.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        assertTrue(inflectionComparatorSut.compare(inflectionStub, inflectionStub2) < 0);
    }

    @Test
    public void testOnCategoryOnlyReverse() {
        when(inflectionStub.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        when(inflectionStub2.getCategory()).thenReturn(InflectionCategory.PLURALIZATION_AND_CONJUGATION_WITH_S);
        assertTrue(inflectionComparatorSut.compare(inflectionStub, inflectionStub2) > 0);
    }

    @Test
    public void testOnEqualCategories() {
        when(inflectionStub.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        when(inflectionStub2.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        when(inflectionStub.getCompleteInflection()).thenReturn("ADMONITORY");
        when(inflectionStub2.getCompleteInflection()).thenReturn("ADMONITORILY");
        assertTrue(inflectionComparatorSut.compare(inflectionStub, inflectionStub2) < 0);
    }

    @Test
    public void testOnEqualCategoriesEqualL() {
        when(inflectionStub.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        when(inflectionStub2.getCategory()).thenReturn(InflectionCategory.OTHER_NOUNS_INCL_PLNOUN);
        when(inflectionStub.getCompleteInflection()).thenReturn("ADJUSTABLE");
        when(inflectionStub2.getCompleteInflection()).thenReturn("ADJUSTABLY");
        assertTrue(inflectionComparatorSut.compare(inflectionStub, inflectionStub2) < 0);
    }

}
