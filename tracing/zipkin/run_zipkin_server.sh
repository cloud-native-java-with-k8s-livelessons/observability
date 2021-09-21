#!/usr/bin/env bash

docker run -d -p 9411:9411 openzipkin/zipkin

# export KAFKA_ZOOKEEPER=127.0.0.1:2181
# cd `dirname $0` 
# java -jar zipkin.jar
