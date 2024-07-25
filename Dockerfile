FROM ubuntu:20.04

# Instalar dependências
RUN apt-get update && apt-get install -y \
    curl \
    gnupg \
    lsb-release \
    software-properties-common \
    rabbitmq-server \
    openjdk-11-jdk \
    maven \
    mongodb

# Copiar arquivos da aplicação
WORKDIR /app
COPY . .

# Garantir que o start.sh tem permissões de execução
RUN chmod +x /app/start.sh

# Construir a aplicação Java
RUN mvn clean package

# Definir variáveis de ambiente
ENV RABBITMQ_DEFAULT_USER=admin
ENV RABBITMQ_DEFAULT_PASS=admin
ENV MONGO_INITDB_ROOT_USERNAME=admin
ENV MONGO_INITDB_ROOT_PASSWORD=admin
ENV MONGO_INITDB_DATABASE=btg

# Expor portas
EXPOSE 8080 5672 15672 27017

# Iniciar todos os serviços
CMD ["sh", "/app/start.sh"]
