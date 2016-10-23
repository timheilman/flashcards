package crosswords.flashcards.domain.inflections.impl;

import crosswords.flashcards.domain.Inflections;
import crosswords.flashcards.domain.inflections.InflectionComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class InflectionsImplTest {
    private Inflections inflectionsSut;
    @Mock
    private InflectionComparator inflectionComparatorMock;

    @Before
    public void init() {
        initMocks(this);

        inflectionsSut = new InflectionsImpl(
                inflectionComparatorMock);
    }

    @Test
    public void testCtor() {
        assertEquals(inflectionComparatorMock, inflectionsSut.comparator());
    }
}
