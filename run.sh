#!/bin/sh
# Compiles the game into out/ and starts it. Options are passed on, e.g.  ./run.sh --plain
cd "$(dirname "$0")"
mkdir -p out && javac -d out -sourcepath src src/Main.java && java -cp out Main "$@"
