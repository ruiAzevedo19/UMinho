#!/usr/bin/env bash
i=0;
while [ $i -le 5 ]
  do
    echo "Number of client threads: $((2**i))" >> out
    echo "Client: $((2**i))"
    eval "java -jar benchmark-1.0.jar --user abduser --password segredo -D"
    eval "java -jar benchmark-1.0.jar --user abduser --password segredo -p"
    eval "java -jar benchmark-1.0.jar --user abduser --password segredo --clients $((2**i)) -x -R 20 -W 10 >> out"
    i=$((i + 1))
  done

