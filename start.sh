#!/bin/bash

# Iniciar MongoDB
mongod --fork --logpath /var/log/mongodb.log --dbpath /var/lib/mongodb --bind_ip_all

## Configurar MongoDB
mongo <<EOF
use ${MONGO_INITDB_DATABASE}
db.createUser({
  user: '${MONGO_INITDB_ROOT_USERNAME}',
  pwd: '${MONGO_INITDB_ROOT_PASSWORD}',
  roles: [{
    role: 'readWrite',
    db: '${MONGO_INITDB_DATABASE}'
  }]
})
EOF

# Iniciar RabbitMQ
service rabbitmq-server start
command rabbitmq-plugins enable rabbitmq_management
command rabbitmqctl add_user ${RABBITMQ_DEFAULT_USER} ${RABBITMQ_DEFAULT_PASS}
command rabbitmqctl set_user_tags ${RABBITMQ_DEFAULT_USER} administrator
command rabbitmqctl set_permissions -p "/" "admin" ".*" ".*" ".*"
command rabbitmqadmin declare queue --vhost=/ name=desafio_btg_pedidos durable=true

# Iniciar aplicação Spring Boot
java -jar /app/target/desafio_eng_software-1.0-SNAPSHOT.jar

sleep infinity
