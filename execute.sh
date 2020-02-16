#!/bin/bash

relative_directory="src/Class/"
cd ${relative_directory}
absolute_directory=`pwd`

CLASSPATH=$absolute_directory

java Main &
