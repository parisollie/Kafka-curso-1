brew services start zookeeper

brew services start kafka

--Vemos todos los topics

kafka-topics --bootstrap-server localhost:9092 --list 

--Eliminar un topic

kafka-topics --bootstrap-server localhost:9092 --topic __consumer_offsets --delete

# Ver los consumer groups
kafka-consumer-groups --bootstrap-server localhost:9092 --list

#Vid 45

--Primero creamos el topic
 
kafka-topics --bootstrap-server localhost:9092 --topic demo_java --create --partitions 3 --replication-factor 1

--Vemos el topic que creamos
kafka-console-consumer --bootstrap-server localhost:9092 --topic demo_java --from-beginning
echo $PATH

#Vid 60 creamos un topic

kafka-topics --bootstrap-server localhost:9092 --topic wikimedia.recentchanges --create --partitions 3 --replication-factor 1

#Vemos el topic
kafka-console-consumer --bootstrap-server localhost:9092 --topic wikimedia.recentchanges

#Vid 74, Docker

http://localhost:9200/

http://localhost:5601


#Vid 76
GET /

PUT /my-first-index

PUT /my-first-index/_doc/1
{"Description": "To be not to be,that is the question."}

GET /my-first-index/_doc/1

DELETE /my-first-index/_doc/1

DELETE /my-first-index

GET /my-first-index/_doc/

#En la seccion 3 , necesitamos la consola , no la podemos hacer 
#sino hacemos la consola

#Seccion 15 , pura teoria

#Seccion 16 , pura teoria

#seccion 17
#V-111
kafka-topics --create --bootstrap-server localhost:9092 --topic configured-topic --replication-factor 1 --partitions 3

kafka-topics --bootstrap-server localhost:9092 --topic configured-topic --describe

kafka-configs --bootstrap-server localhost:9092 --entity-type topics  --entity-name configured-topic --describe

kafka-configs --bootstrap-server localhost:9092 --entity-type topics  --entity-name configured-topic --alter --add-config min.insync.replicas=2

kafka-configs --bootstrap-server localhost:9092 --entity-type topics  --entity-name configured-topic --describe

kafka-topics --bootstrap-server localhost:9092 --topic configured-topic --describe

kafka-configs --bootstrap-server localhost:9092 --entity-type topics  --entity-name configured-topic --alter --delete-config min.insync.replicas

kafka-topics --bootstrap-server localhost:9092 --topic configured-topic --describe

#V-113

kafka-topics --bootstrap-server localhost:9092 --describe --topic configured-topic __consumer_offsets

#V-116

https://learn.conduktor.io/kafka/kafka-topic-configuration-log-compaction/

kafka-topics --bootstrap-server localhost:9092 --create --topic employee-salary \
    --partitions 1 --replication-factor 1 \
    --config cleanup.policy=compact \
    --config min.cleanable.dirty.ratio=0.001 \
    --config segment.ms=5000

kafka-topics --bootstrap-server localhost:9092 --topic employee-salary --describe

kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic employee-salary \
    --from-beginning \
    --property print.key=true \
    --property key.separator=,

kafka-console-producer --bootstrap-server localhost:9092 \
    --topic employee-salary \
    --property parse.key=true \
    --property key.separator=,

Patrick,salary: 10000
Lucy,salary: 20000
Bob,salary: 20000
Patrick,salary: 25000
Lucy,salary: 30000
Patrick,salary: 30000

Stephane,salary: 0