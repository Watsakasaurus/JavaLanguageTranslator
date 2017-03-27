#!/usr/bin/bash
#the translation program made some translations into uppercase,
#it is a good idea to ensure all strings are entirely lowercase
filename="$1"
while read -r line
do
	name=${line,,} 
	echo $name >> $filename.change
done < "$filename"
