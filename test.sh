#!/bin/sh
# Run feature checks and a set of simple balance simulations.   ./test.sh 3000 for more balance samples
cd "$(dirname "$0")"
mkdir -p out && javac -d out -sourcepath src test/FeatureTest.java test/BalanceTest.java
java -cp out FeatureTest
java -cp out BalanceTest "$@"
