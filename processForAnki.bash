#!/bin/bash

# invoke run to update dictionaries/union_non_sowpods.txt
cd flashcard-processing
/opt/apache-maven/bin/mvn test exec:exec

if [ $? -ne 0 ]; then
echo mvn failed!
exit 400
fi

cd ..

#order matters when creating a new deck!
#2LW
grep '^..\t' dictionaries/union_non_sowpods.txt > ankiImports/masterImport.txt
#3LW
grep '^...\t' dictionaries/union_non_sowpods.txt >> ankiImports/masterImport.txt
#45JQXZ
grep '^.....\?\t' dictionaries/union_non_sowpods.txt | grep '^[A-Z]*[JQXZ][A-Z]*\t' >> ankiImports/masterImport.txt
#no-aeiou
grep -v '^[A-Z]*[AEIOU][A-Z]*\t' dictionaries/union_non_sowpods.txt >> ankiImports/masterImport.txt
#from play or study
cat wordlists/learnedFromPlayOrStudy.txt | awk '{ if (FILENAME == "-") { print toupper($1) "\t" dict[toupper($1)]; } else { split($0, s, /\t/); dict[s[1]] = s[2]; } }' dictionaries/union_non_sowpods.txt - >> ankiImports/masterImport.txt

sort ankiImports/masterImport.txt | uniq > ankiImports/tmp
mv ankiImports/tmp ankiImports/masterImport.txt

# masterImport.txt should be ready for import with settings "update existing cards"
