FROM openjdk:8-jdk

WORKDIR /zookeeper

# Get Zookeeper
RUN apt-get update -q && \
    apt-get install wget -y -q && \
    wget -q https://downloads.apache.org/kafka/3.5.0/kafka_2.12-3.5.0.tgz && \
    tar -zxf kafka_2.12-3.5.0.tgz

# 2181: Port for client
EXPOSE 2181

# Make directory for storing data
RUN mkdir -p /data

# Copy Zookeeper configuration file
COPY zoo.properties /zookeeper/kafka_2.12-3.5.0/config/

# Copy script file to run ZooKeeper.
COPY run-zookeeper.sh .

# Grant permission to run-zookeeper.sh
RUN chmod +x run-zookeeper.sh

# Replace CRLF to LF in .sh file
RUN sed -i 's/\r$//' run-zookeeper.sh

# Start Zookeeper Server
ENTRYPOINT [ "bash", "run-zookeeper.sh" ]