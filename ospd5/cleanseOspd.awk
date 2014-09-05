# invocation:
# tr because awk doesn't handle 0x00 in input well
# first sed handles pure-ASCII markup for italics replacement with _underscores_
# second sed handles pure-ASCII markup for bold replacement with *asterisks*
# cat ospd_orig_cropped.dat | tr '\000' '\001' | awk -f cleanseOspd.awk | sed 's/`i\([^`]*\)`n/_\1_/g' | sed 's/`b\([^`]*\)`n/*\1*/g' | sort > ./ospd5.txt

{
	# replace unicode (?) apostrophes 0xE28099 that show up in entry definitions with 0x27 AKA \047 aka '
	# not-doing this causes the 0xE2 to prematurely end definitions based on the heuristic below
	gsub(/\342\200\231/, "'", $0);

	# find the position of 0x6062, the all-caps entry word, 0x606E20; its definition begins in ascii after this
	firstWordPosLessTwo=match($0, /\140\142[A-Z]*\140\156\040/);
	while (firstWordPosLessTwo != 0) {
#		i=i+1;
		# clip the leading 0x6062
		$0 = substr($0, firstWordPosLessTwo + 2);
		# (heuristic) the entry definition ends with anything other than other than LF 0x0A (since I'm on a mac) or a printable ascii character (0x20 through 0x7E)
		firstNonprintPos=match($0, /[^\012\040-\176]/);
		if (firstNonprintPos == 0) {
			# once in a while the entry definition ends with a newline instead; awk's idea of the record is now done processing
			entry = $0;
		} else {
			# otherwise apply the heuristic
			entry = substr($0, 0, firstNonprintPos-1);
		}
		# the leading (pure-ascii-markup) `n should become a tab 0x09
		sub(/`n/, "\011", entry);
		print entry;
#	print NR, i, entry;
#	if (NR ==7 && i == 8) print
		# find the next position of 0x6062, the all-caps entry word, 0x606E20; its definition begins in ascii after this
		firstWordPosLessTwo=match($0, /\140\142[A-Z]*\140\156\040/);
	}
}