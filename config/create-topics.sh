echo "Aguardando Kafka iniciar..."
sleep 20

kafka-topics.sh --create --bootstrap-server kafka:9092 --replication-factor 3 --partitions 4 --TOPIC_TRANSACTIONS


echo "Tópicos criados com sucesso."
