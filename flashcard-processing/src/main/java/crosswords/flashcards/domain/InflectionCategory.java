package crosswords.flashcards.domain;

public enum InflectionCategory {
    // Box 1: S, ED, ING, and co
    PLURALIZATION_AND_CONJUGATION_WITH_S,
    ED,
    ING,
    INGS,
    ED_ING_THEN_LY_AND_NESS,
    // Box 2: ER, ERS, EST, LY
    ER_AND_ERS,
    EST,
    LY,
    // Box 3: Y, LESS, LIKE, other adj and co
    Y_THEN_PLURALS_LY_ER_EST_NOUN,
    LESS_AND_LIKE,
    OTHER_ADJ_THEN_LY_NOUN_PLNOUN,
    // Box 4: Transition to nouns FUL and co, people nouns
    FUL_AND_CO,
    PEOPLE_NOUNS_INCL_PLNOUN,
    // Box 5: other nouns and misc
    OTHER_NOUNS_INCL_PLNOUN,
    MISC_FREQ_OVER_10_SOWPODS,
    MISC_FREQ_10_OR_UNDER_SOWPODS,
    ALTERNATE_SPELLING,
    SHORTENING
}
