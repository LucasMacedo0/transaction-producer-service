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
Abaixo está um resumo de como funciona o fluxo da API ao processar uma transação ou depósito, e também como lidamos com falhas:

Fluxo principal:
O cliente envia uma requisição HTTP com os dados da transação ou depósito.

A API recebe a requisição, valida os dados e prepara a mensagem.

Após isso, os dados são enviados para o tópico principal do Kafka (TRANSACTIONS_TOPIC).

Outros serviços que consomem esse tópico recebem a mensagem e fazem o processamento necessário.

Em caso de erro no envio:
Se der algum erro ao enviar a mensagem, a API tenta reenviar automaticamente até 3 vezes, com um intervalo de 5 segundos entre as tentativas.

Se mesmo assim não funcionar, a mensagem é enviada para um tópico de erros (DLT – TOPIC_TRANSACTIONS.DLT).

Reprocessamento do DLT:
Existe um consumidor escutando o tópico DLT. Quando uma mensagem cai lá, esse consumidor tenta reenviar para o tópico original.

Esse reprocessamento também tem um limite de 3 tentativas, controlado pelo header retryCount.

Se estourar esse limite, a mensagem continua no DLT, mas não será mais processada automaticamente.

Caso necessário, dá pra criar um endpoint depois pra reprocessar essas mensagens manualmente.




## **Swagger**
 http://localhost:8080/producer-service/swagger-ui/index.html


