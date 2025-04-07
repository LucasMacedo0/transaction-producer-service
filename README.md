# **transaction-producer-service**
### **API Produtora de Transações**
A `transaction-producer-service` é uma API responsável por publicar informações de **transações financeiras**, incluindo depósitos e transferências entre contas, para um tópico Kafka. A API faz parte de um sistema de processamento de transações financeiras distribuído, permitindo o envio de mensagens em tempo real para outros serviços que consumirão as transações para posterior processamento.
## **Descrição**
A `transaction-producer-service` processa requisições HTTP para criar transações financeiras, como depósitos bancários e transferências entre contas, enviando essas informações para um tópico Kafka. Com validação robusta das entradas, a API garante que só mensagens seguras e consistentes sejam publicadas no Kafka. Ela utiliza o **Spring Kafka** para lidar com a produção de mensagens e se integra facilmente a sistemas consumidores, promovendo alta escalabilidade no processamento de transações.
## **Principais Funcionalidades**
- ✉️ **Recebimento de Transações:**
    - Recebe informações sobre depósitos ou transferência entre contas por meio de requisições HTTP.

- ✅ **Validação de Dados:**
    - Valida detalhadamente os campos da requisição usando **Jakarta Bean Validation**, garantindo que apenas informações válidas sejam publicadas.

- 📡 **Envio para Kafka:**
    - Publica mensagens das transações em um tópico Kafka configurado no sistema.

- 📜 **Logs e Monitoramento:**
    - Registra operações utilizando logs para rastreamento e monitoramento.

## **Endpoints Disponíveis**
### 1. **Depósito**
- **URL:** `/deposits`
- **Método:** `POST`
- **Descrição:** Realiza o depósito em uma conta específica. Envia as informações para o respectivo tópico Kafka.
- **Corpo da Requisição:**
``` json
  {
      "accountNumber": "123456",
      "branchCode": "001",
      "bankName": "Banco Exemplo",
      "accountType": "CORRENTE",
      "cpfCnpj": "12345678901",
      "owner": {
          "name": "João Silva",
          "email": "joao@email.com"
      },
      "amount": 150.50
  }
```
### 2. **Transferência**
- **URL:** `/transactions`
- **Método:** `POST`
- **Descrição:** Realiza uma transação de transferência entre contas específicas. Publica as informações no tópico Kafka para processamento.
- **Corpo da Requisição:**
``` json
  {
      "accountNumber": "123456",
      "targetAccountNumber": "654321",
      "customerIdentifier": "12345678901",
      "amount": 250.75
  }
```
## **Tecnologias Utilizadas**
- 🚀 **Spring Boot:** Framework principal para o desenvolvimento da API.
- 💬 **Apache Kafka:** Sistema de mensagens para comunicação entre produtores e consumidores.
- 🛠️ **Spring Kafka:** Integração entre Spring e Kafka para facilitar o envio de mensagens.
- ✔ **Jakarta Bean Validation:** Para validação dos dados recebidos nas requisições.
- ✍️ **Lombok:** Redução da verbosidade do código com geração automática de getters, setters e outros utilitários.

## **Como Executar o Projeto**
### 1. **Pré-requisitos**
- Java 17+.
- Apache Kafka configurado e em execução.
- Maven 3.x instalado.

### 2. **Configurações do Kafka**
No arquivo `application.yml`, insira a configuração para conexão com o Kafka:
``` yaml
spring:
  kafka:
    bootstrap-servers: <SEU_SERVIDOR_KAFKA>
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```
### 3. **Build e Execução**
- Compile o projeto com Maven:
``` 
  mvn clean install
```
- Execute o serviço:
``` 
  java -jar target/transaction-producer-service.jar
```
## **Estrutura de Diretórios**
``` plaintext
src/
├── main/
│   ├── java/
│   │   └── com.example.transactionproducerservice
│   │       ├── config/           # Configurações (Kafka, Application)
│   │       ├── Controller/       # Endpoints
│   │       ├── domain/           # Modelos de Dominio (Ex.: DepositRequest, TransactionWithAccount)
│   │       ├── Exception/        # Tratamento das Exceções
│   │       └── Service/          # Lógica de negócio
│   └── resources/
│       ├── application.yml       # Configurações do ambiente
└── test/                         # Testes unitários e de integração
```
## **Fluxo de Comunicação da API**
Abaixo está o **fluxo simplificado** de como as requisições são processadas pela API e enviadas para o Kafka:
1. O cliente realiza uma requisição HTTP com os dados da transação.
2. A API recebe e valida os dados.
3. Após validação, a transação é enviada para o tópico Kafka configurado.
4. Outros serviços registrados no Kafka consomem as mensagens publicadas para processamento posterior.