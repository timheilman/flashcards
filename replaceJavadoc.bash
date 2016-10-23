#!/bin/bash

for filein in `find . -name '*.java'`; do
    awk -f replaceJavadoc.awk $filein > tmp
    mv tmp $filein
    git add $filein
done
