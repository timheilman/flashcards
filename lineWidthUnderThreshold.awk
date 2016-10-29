{ if (length($0) > 120) tooLong=1; }
END { if (tooLong == 1) print "tooLong" }
