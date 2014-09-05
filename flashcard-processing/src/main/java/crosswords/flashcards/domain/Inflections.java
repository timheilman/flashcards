package crosswords.flashcards.domain;

import java.util.SortedSet;

/**
 * Created by tim on 8/24/14.
 */
public interface Inflections extends SortedSet<Inflection> {
    boolean add(Inflection inflection);
}
