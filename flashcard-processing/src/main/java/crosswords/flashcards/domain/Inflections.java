package crosswords.flashcards.domain;

import java.util.SortedSet;

public interface Inflections extends SortedSet<Inflection> {
    boolean add(Inflection inflection);
}
