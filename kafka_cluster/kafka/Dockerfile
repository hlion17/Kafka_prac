FROM openjdk:8-jdk

WORKDIR /kafka

# Get kafka
RUN apt-get update -q && \
    apt-get install wget -y -q && \
    wget -q https://downloads.apache.org/kafka/3.5.0/kafka_2.12-3.5.0.tgz && \
    tar -zxf kafka_2.12-3.5.0.tgz

# Default port for Kafka
EXPOSE 9092

# Make Kafka log directory
RUN mkdir -p /data

# Copy script to run kafka
COPY run-kafka.sh .

# Grant permission to run-kafka.sh file
RUN chmod -x run-kafka.sh

# Replace CRLF to LF in .sh file
RUN sed -i 's/\r$//' run-kafka.sh

# kafka Broker start
CMD [ "bash", "./run-kafka.sh"]