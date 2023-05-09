#/bin/bash

mvn versions:set -DnewVersion="$1"
mvn versions:commit # to remove temporary files