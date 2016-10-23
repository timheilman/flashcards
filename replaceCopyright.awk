function commentWasThreeLinesAndCreatedByFound() {
    if (commentLines == 3 && foundCreatedByLine == 1) return 1;
    return 0;
}

/\/\*/ { commentOpen=1; commentLines=0; commentBuffer="" }
/[cC]opyright/ { if (commentOpen) foundCreatedByLine=1; }
{ if (commentOpen) { commentBuffer = commentBuffer $0 "\n"; commentLines++; }
    else print }
/\*\// { if (!commentWasThreeLinesAndCreatedByFound()) printf("%s", commentBuffer);
    commentOpen=0; foundCreatedByLine=0;}

