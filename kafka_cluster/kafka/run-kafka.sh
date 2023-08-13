#!/bin/bash

cd /kafka/kafka_2.12-3.5.0

# Set log file directory
sed -i 's/log.dirs=\/tmp\/kafka-logs/log.dirs=\/data/g' config/server.properties

# Set Broker ID
# sed -i 's/broker.id=0/broker.id='${HOST_ID}'/' config/server.properties
# Set ZooKeeper connection
# sed -i 's/zookeeper.connect=localhost:2181/zookeeper.connect=zookeeper-0.zookeeper.default.svc.cluster.local:2181,zookeeper-1.zookeeper.default.svc.cluster.local:2181,zookeeper-2.zookeeper.default.svc.cluster.local:2181/' config/server.properties
sed -i 's/zookeeper.connect=localhost:2181/zookeeper.connect=zookeeper:2181/g' config/server.properties

# Remove unnecessary files of /data (Log file directory)
rm -rf /data

sh bin/kafka-server-start.sh config/server.properties