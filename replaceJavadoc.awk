function javaDocFound() {
    if (foundJavaDocLine == 1) return 1;
    return 0;
}

/\/\*/ { commentOpen=1; commentLines=0; commentBuffer="" }
/@[Pp]aram/ { if (commentOpen) foundJavaDocLine=1; }
/@[Rr]eturn/ { if (commentOpen) foundJavaDocLine=1; }
{ if (commentOpen) { commentBuffer = commentBuffer $0 "\n"; commentLines++; }
    else print }
/\*\// { if (!javaDocFound()) printf("%s", commentBuffer);
    commentOpen=0; foundJavaDocLine=0;}

