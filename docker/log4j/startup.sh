#!/usr/bin/env bash
FILE=/home/ubuntu/Log4J.lck

#Iniciando aplicações da API
/usr/bin/java -jar /home/ubuntu/jar/LDAPServer.jar &
/usr/bin/java -jar /home/ubuntu/jar/undertow.jar &

while true
do
        if [ -f "$FILE" ]; then
                sleep 20
                rm -f /home/ubuntu/Log4J.lck
                rm -f /home/ubuntu/flag.txt
                /usr/bin/java -jar /home/ubuntu/jar/undertow.jar &
        else
                true
        fi
        sleep 10
        echo "Executei" >/dev/null
done