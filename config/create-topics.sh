#!/bin/bash

echo "Aguardando Kafka iniciar..."
sleep 20

# Criar o tópico principal
kafka-topics.sh --create \
  --bootstrap-server kafka:9092 \
  --replication-factor 3 \
  --partitions 4 \
  --topic TOPIC_TRANSACTIONS

# Criar o tópico DLT (Dead Letter Topic)
kafka-topics.sh --create \
  --bootstrap-server kafka:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic TOPIC_TRANSACTIONS.DLT

echo "Tópicos criados com sucesso."
