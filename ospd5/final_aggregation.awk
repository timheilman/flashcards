#usage: awk -f final_aggregation.awk deduped_ospd5_restricted.txt wwf4_enable1_union.txt
{
    if (FILENAME == "wordlists/greenworm_dot_net_wwf_v4point0.txt") {
	wwf4[toupper($0)] = 1;
    } else if (FILENAME == "wordlists/enable1.txt") {
	enable1[toupper($0)] = 1;
    } else if (FILENAME == "-") {
	dict[$1] = $0;
	# i = i + 1;
	# if (i % 1000 == 0) {
	#     print;
	# }
    } else {
	word=toupper($1);
	if (dict[word] != "" ) {
	    print dict[word];
	} else {
	    printf("%s\t %se1 %sw4 -o5\n",
		   word,
		   enable1[word] == 1 ? "+" : "-",
		   wwf4[word] == 1 ? "+" : "-");
	}
    }
}
