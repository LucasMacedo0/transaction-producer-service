transaction-producer-service - API Produtora de Transações
A transaction-producer-service é uma API responsável por enviar informações de transações financeiras, incluindo depósitos e outros tipos de transações, para um tópico Kafka. Esta API faz parte de um sistema de processamento de transações, onde as informações são publicadas em tempo real para processamento posterior por consumidores de mensagens.

Descrição
A transaction-producer-service recebe requisições HTTP para criar transações financeiras, como depósitos em contas bancárias, e as envia para um tópico Kafka. A API valida as informações da transação recebida e utiliza o Kafka Producer para publicar as mensagens no tópico, permitindo que outros serviços (como o serviço de processamento de transações) consumam essas informações.

Funcionalidades
Recebe informações de transações financeiras via requisição HTTP.

Suporta transações de tipos variados, como depósitos.

Valida os dados de entrada utilizando Jakarta Bean Validation.

Envia as informações de transação para um tópico Kafka.

Utiliza Spring Kafka para a comunicação com o Kafka.

Tecnologias Utilizadas
Spring Boot: Framework principal para o desenvolvimento da API.

Kafka: Sistema de mensageria utilizado para comunicação entre produtores e consumidores de dados.

Spring Kafka: Integração do Spring com o Kafka para envio e recebimento de mensagens.

Jakarta Bean Validation: Para validação dos dados de entrada.

Lombok: Biblioteca para reduzir a verbosidade do código (ex: getters, setters e construtores).

Pré-requisitos
Antes de rodar a aplicação, verifique se você tem os seguintes itens instalados:

JDK 17 ou superior.

Apache Kafka em funcionamento (ou pode ser usado via Docker).

Maven ou Gradle para gerenciamento de dependências e build.