#!/bin/sh

filesOver500=""
linesOver120=""
for file in `git diff --cached --name-only`; do
    if [ -r "$file" ]; then
	basenameOfFile=`basename $file`
	if [ `wc -l $file | awk '{ print $1 }'` -gt 500 ]; then
	    filesOver500="$filesOver500 $basenameOfFile";
	fi
	linesTooLong=`awk -f lineWidthUnderThreshold.awk $file`
	if [ ! -z "$linesTooLong" ]; then
	    linesOver120="$linesOver120 $basenameOfFile";
	fi
    fi
done

if [ ! -z "$filesOver500" -o ! -z "$linesOver120" ]; then
    echo "Commit rejected;"
    if [ ! -z "$filesOver500" ]; then
	echo "  Files over 500 lines: " $filesOver500;
    fi
    if [ ! -z "$linesOver120" ]; then
	echo "  Files with lines over 120 characters: " $linesOver120;
    fi
    exit 1
fi

exit 0
