#!/bin/bash
source scripts/set-dev-env.sh
gradle build
unzip -o -q build/distributions/CsvValidator-1.0-SNAPSHOT.zip -d build/distributions
sh build/distributions/CsvValidator-1.0-SNAPSHOT/bin/CsvValidator