#!/bin/bash

FILENAME="temp.tsv"

java RunCartograph > $FILENAME

# python PlotCartograph.py $FILENAME

RESPONSE="-"
echo $RESPONSE
while [ "$RESPONSE" != "y" ] && [ "$RESPONSE" != "n" ]; do
  echo "Would you like to save the file? y/n"
  read RESPONSE
  echo $RESPONSE
done

if [ "$RESPONSE" = "y" ]; then
  echo "What would you like to call the file?"
  read MYFILENAME
  mv $FILENAME $MYFILENAME
else 
  echo "No file for you"
  rm $FILENAME
fi


