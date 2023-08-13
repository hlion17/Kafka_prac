#!/bin/bash

# Set Zookeeper Server Id
echo 1 > /data/myid

# Run Zookeeper Server
sh /zookeeper/kafka_2.12-3.5.0/bin/zookeeper-server-start.sh /zookeeper/kafka_2.12-3.5.0/config/zoo.properties