# Desafio Engenheiro de Software - BTG

## Descrição

Este projeto é uma aplicação para consumir uma fila de pedidos, salvar no MongoDB e expor uma API REST com métodos de consulta sobre esses pedidos. É desenvolvido utilizando Java 8 + Spring Boot, RabbitMQ, SQS, MongoDB, entre outros.

## Configuração e Instalação

Este projeto foi desenvolvido pensando na execução 100% conteinerizada, para executar basta:

1. **Clonar o repositório**

   ```bash
   git clone https://github.com/isabelafgd/desafio_eng_software.git
   cd desafio_eng_software
   
2. **Construir a imagem**

   ```bash
   docker build -t desafio_eng_software .

3. **Executar o container**

   ```bash
   docker run -d -p 8080:8080 -p 15672:15672 -p 5672:5672 -p 27017:27017 desafio_eng_software

## Uso

1. **RabbitMQ**

- É possivel interagir com a interface de gerenciamento do RabbitMQ acessando http://localhost:15672/
- Usuário: admin
- Senha: admin
- Dessa forma, é possível adicionar uma nova mensagem à fila desafio_btg_pedidos, que já estará criada
  - Exemplo de mensagem:

     ```bash
     {
      "codigoPedido": 1001,
      "codigoCliente":1,
      "itens": [
        {
          "produto": "lápis",
          "quantidade": 100,
          "preco": 1.10
        },
        {
          "produto": "caderno",
          "quantidade": 10,
          "preco": 1.00
        }
      ]
    }

2. **MongoDB** 

- É possível acessar o MongoDB pela porta 27017

     ```bash
     mongo --host localhost --port 27017 -u admin -p admin --authenticationDatabase btg

3. **API REST** 

As chamadas para API devem ser feitas na porta 8080, os endpoints disponíveis são:

- http://localhost:8080/api/pedido/{codigoPedido}/valor
- http://localhost:8080/api/cliente/qtdPedidos
- http://localhost:8080/api/cliente/{codigoCliente}/qtdPedidos
- http://localhost:8080/api/cliente/{codigoCliente}/pedidos 

Além disso, é possível encontrar a collection do Postman no diretório /src/main/resources.

## Testes

No diretório src/test/java/desafio/functional encontra-se o teste funcional, que executa desde o consumo da mensagem no RabbitMQ até as chamadas de todos os endpoints da API.