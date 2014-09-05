#usage: awk -f annotateOspd5WithEnableAndWwf.awk wordlists/greenworm_dot_net_wwf_v4point0.txt /enable1.txt /ospd5.txt

# overall structure: 
# 1. read all files intended as word lists
# 2. parse entry fields
# 3. compute good stems of this entry word
# 4. output the entry, if necessary
# 5. output any good stems, if necessary

{
    if (FILENAME == "wordlists/greenworm_dot_net_wwf_v4point0.txt") {
	wwf4[toupper($0)] = 1;
    } else if (FILENAME == "wordlists/enable1.txt") {
	enable1[toupper($0)] = 1;
    } else {
	split($0, word_stemdef, /\t/);
	entryWord = word_stemdef[1];
	remainder = word_stemdef[2];
	
#    print entryWord; 

	# advance past the space
#    print "before space adavance, rest is '" remainder "'";
	sub(/ /, "", remainder);
    
#    print "before part of speech, rest is '" remainder "'";
	# advance past any part-of-speech
	match(remainder, /^_[a-z, ]*_ /);
	partOfSpeech = substr(remainder, RSTART, RLENGTH-1);
	if (partOfSpeech != "") {
	    partOfSpeech = partOfSpeech " ";
	}
	sub(/^_[a-z, ]*_ /, "", remainder);
#    print "part of speech is " partOfSpeech " rest is " remainder;

	goodStems = "";
	goodStemWords = "";
	while (match(remainder, /^\*-?[A-Z]*\*/) != 0) {
	    stem = substr(remainder, RSTART, RLENGTH);
#	print "stem is " stem;
	    if (match(remainder, /^\*-[A-Z]*\*/) != 0) {
		suffix = substr(remainder, RSTART+2, RLENGTH-3);
		suffixes[suffix] = suffixes[suffix] + 1;
		stemWord = entryWord suffix

#	    print "found suffix stem word " stemWord
		# loop increment
		sub(/^\*-[A-Z]*\*,? /, "", remainder);
	    } else {
		match(remainder, /^\*[A-Z]*\*/);
		stemWord = substr(remainder, RSTART+1, RLENGTH-2);
		exactStemWords[stemWord] = exactStemWords[stemWord] + 1;
#	    print "found exact stem word" stemWord
		# loop increment
		sub(/^\*[A-Z]*\*,? /, "", remainder);
	    }
	    if (enable1[stemWord] == 1 || wwf4[stemWord] == 1) {
#		print "found good stem word" stemWord
#		print "stem is" stem
		if (goodStems == "") {
		    goodStems = stem;
		    goodStemWords = stemWord;
		} else {
		    goodStems = goodStems ", " stem;
		    goodStemWords = goodStemWords " " stemWord;
		}
	    } else {
#		print "found bad stem word" stemWord
	    }

	}
	
	if (goodStems != "") {
	    goodStems = goodStems " ";
	}
	definition = remainder;
	### DONE PARSING ENTRY

	### OUTPUT ENTRY
	split(goodStemWords, goodStemWordArray);

	if (enable1[entryWord] || wwf4[entryWord]) {
	    printf("%s\t %se1 %sw4 %s%s%s\n",
	    	   entryWord,
	    	   enable1[entryWord] == 1 ? "+" : "-",
	    	   wwf4[entryWord] == 1 ? "+" : "-",
	    	   partOfSpeech,
	    	   goodStems,
	    	   definition);
	
	}

	for (goodStemIndex in goodStemWordArray) {
	    goodStemWord = goodStemWordArray[goodStemIndex];
	    printf("%s\t %se1 %sw4 %sre1 %srw4 %s %s%s%s\n",
	    	   goodStemWord,
	    	   enable1[goodStemWord] == 1 ? "+" : "-",
	    	   wwf4[goodStemWord] == 1 ? "+" : "-",
	    	   enable1[entryWord] == 1 ? "+" : "-",
	    	   wwf4[entryWord] == 1 ? "+" : "-",
	    	   entryWord,
	    	   partOfSpeech,
	    	   goodStems,
	    	   definition);
	}	    
	
    }
}
END {
    # for (suffix in suffixes) {
    # 	print  suffixes[suffix], "-"suffix;
    # }
    # for (exactStemWord in exactStemWords) {
    # 	print exactStemWords[exactStemWord], exactStemWord;
    # }
}
