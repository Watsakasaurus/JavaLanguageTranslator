#!/usr/bin/bash
#translates a big file of english words to a big file of another language
#correspondance is direct line to line 
filename="$1"
language="$2"
while read -r line
do
	name=$(trans -b :$language $line)
	echo $name >> $language.txt
	echo $name
done < "$filename"
