#!/bin/bash
ORIG_FILE=$1
NEWS_FILE=$2
if [ -z "$NEWS_FILE" ]; then
    NEWS_FILE=emptyfile
fi
OLDS_FILE=$3
if [ -z "$OLDS_FILE" ]; then
    OLDS_FILE=emptyfile
fi

for wordLength in {1..15}; do
    regexp="^";
    i=0
    while [ $i -lt $wordLength ]; do
	regexp+=".";
	i=`expr $i + 1`
    done;
    regexp+="$"
    ORIG_COUNT=`grep $regexp $ORIG_FILE | wc -l`
    NEW_COUNT=`grep $regexp $NEWS_FILE | wc -l`
    OLD_COUNT=`grep $regexp $OLDS_FILE | wc -l`
    echo $wordLength : $ORIG_COUNT + $NEW_COUNT '-' $OLD_COUNT = `expr $ORIG_COUNT + $NEW_COUNT '-' $OLD_COUNT`
done
