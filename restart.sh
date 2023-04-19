#!/bin/sh

pid=$(ps -ef| grep fridayboot-api-0.0.1-SNAPSHOT.jar|grep -v grep|awk '{print $2}')
kill ${pid}
nohup /usr/local/btjdk/openjdk17/bin/java -jar -Xmx1024M -Xms256M ./fridayboot-api-0.0.1-SNAPSHOT.jar --server.port=8026 --spring.profiles.active=prod &
