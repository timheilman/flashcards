#!/bin/bash
# for each *entry* word in OSPD5:
inputFile=dictionaries/ospd5.txt
#inputFile=dictionaries/ospd5_just_bo_entry.txt
#inputFile=dictionaries/ospd5_just_ea_entry.txt
#inputFile=dictionaries/ospd5_just_aberrate_entry.txt
#inputFile=dictionaries/ospd5_just_aardwolf_entry.txt
#inputFile=dictionaries/ospd5_just_abacus_entry.txt
#inputFile=dictionaries/ospd5_just_aardwolves_entry.txt
#inputFile=dictionaries/ospd5_just_above_entry.txt
#inputFile=dictionaries/ospd5_just_abune_entry.txt
#inputFile=dictionaries/ospd5_just_aphyllous_entry.txt
#inputFile=dictionaries/ospd5_just_abrading_entry.txt
#inputFile=dictionaries/ospd5_just_bitchery_entry.txt
#inputFile=dictionaries/ospd5_just_shekel_entry.txt

awk -f annotateOspd5WithEnableAndWwf.awk wordlists/greenworm_dot_net_wwf_v4point0.txt wordlists/enable1.txt $inputFile | sort | \
awk '{ if ($1 == lastone) { if ($2 == "ospd5Stem") { print $0; } else { print lastrow; } gotduplicate = 1; } else { if (gotduplicate==0) { print lastrow; } gotduplicate = 0; } lastrow = $0; lastone = $1; lasttwo = $2;  }\
END  { if (gotduplicate==0) { print lastrow; } }'  |\
awk -f final_aggregation.awk wordlists/greenworm_dot_net_wwf_v4point0.txt wordlists/enable1.txt - wordlists/union_enable1_wwf4.txt
