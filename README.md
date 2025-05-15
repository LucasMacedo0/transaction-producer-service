# **transaction-producer-service**
### **API Produtora de Transações**
A transaction-producer-service é uma API responsável por publicar transações financeiras, como depósitos e transferências entre contas, em um tópico Kafka. Faz parte de uma arquitetura de microserviços, permitindo que outras partes do sistema consumam as transações para processamento posterior.
Com validações robustas, a API garante que apenas dados seguros e consistentes sejam enviados para o Kafka. Utilizando o Spring Kafka, ela facilita a integração com sistemas consumidores, promovendo escalabilidade e alta performance no processamento de transações financeiras em tempo real.
## **Principais Funcionalidades**
- ✉️ **Recebimento de Transações:**
    - Recebe informações sobre depósitos ou transferência entre contas por meio de requisições HTTP.

- ✅ **Validação de Dados:**
    - Valida detalhadamente os campos da requisição garantindo que apenas informações válidas sejam publicadas.

- 📡 **Envio para Kafka:**
    - Publica mensagens das transações em um tópico Kafka configurado.

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
## **Como Executar o Projeto**
### 1. **Pré-requisitos**
- Java 17+.
- Apache Kafka configurado e em execução.
- Docker
- Maven 3.x instalado.
- 
## **Como Rodar Local**
- Basta apenas subir o docker-compose.yml que está no diretorio config

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
1. O cliente realiza uma requisição HTTP com os dados da transação ou deposito.
2. A API recebe e valida os dados.
3. Após validação, a transação ou o deposito é enviada para o tópico Kafka configurado.
4. Outros serviços registrados no Kafka consomem as mensagens publicadas para processamento posterior.

## **Swagger**
 http://localhost:8080/producer-service/swagger-ui/index.html


