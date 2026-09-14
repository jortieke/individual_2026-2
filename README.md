# Projeto Individual — Programação Web

Aplicação web desenvolvida para o projeto individual da disciplina de Programação Web, utilizando **Java + Spring Boot** no back-end e **HTML, CSS e JavaScript Vanilla** no front-end.

O projeto consiste em um sistema de cadastro de Digimons, com persistência dos dados em banco de dados relacional e comunicação entre cliente e API REST.

## Tecnologias

### Back-end

* Java
* Spring Boot
* JdbcTemplate
* API REST
* Banco de dados relacional

### Front-end

* HTML5
* CSS3
* JavaScript Vanilla
* Fetch API

Não são utilizados frameworks ou bibliotecas adicionais no front-end.

## Estrutura do projeto

```text
individual_2026-2/
├── digimon/
│   ├── src/
│   └── pom.xml
│
├── front-end/
│   ├── index.html
│   ├── css/
│   └── js/
│
└── README.md
```

## Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/jortieke/individual_2026-2.git
cd individual_2026-2
```

### 2. Executar o back-end

Entre na pasta do projeto Spring Boot:

```bash
cd digimon
```

Execute a aplicação pela IDE ou utilizando o Maven:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Antes da execução, configure no `application.properties` os dados necessários para conexão com o banco de dados.

### 3. Executar o front-end

Abra a pasta `front-end` no Visual Studio Code e execute o arquivo `index.html` utilizando o **Live Server**.

O front-end realiza as requisições para a API através da função `fetch()`.

## Integração Front-end + Back-end

O front-end se comunica com a API REST por meio de requisições HTTP.

O fluxo principal da aplicação é:

```text
Usuário
   ↓
Front-end (HTML + CSS + JavaScript)
   ↓
Fetch API
   ↓
API REST (Spring Boot)
   ↓
Validação dos dados
   ↓
JdbcTemplate
   ↓
Banco de dados
```

O back-end também possui configuração de **CORS**, permitindo que o front-end executado pelo Live Server realize requisições para a API.

## API

### Listar Digimons

```http
GET /digimon
```

Retorna os Digimons cadastrados.

### Buscar Digimon por ID

```http
GET /digimon/{id}
```

Retorna um Digimon específico.

### Cadastrar Digimon

```http
POST /digimon
Content-Type: application/json
```

Exemplo de requisição:

```json
{
  "nome": "Agumon",
  "nivel": "Rookie"
}
```

Em caso de cadastro realizado com sucesso, a API retorna:

```http
201 Created
```

### Atualizar Digimon

```http
PUT /digimon/{id}
```

Atualiza os dados de um Digimon existente. [Não implementado no front-end]

### Remover Digimon

```http
DELETE /digimon/{id}
```

Remove um Digimon cadastrado. [Não implementado no front-end]

## Validação

A aplicação realiza validações tanto no front-end quanto no back-end.

### Front-end

Os dados são validados antes do envio da requisição, verificando campos obrigatórios e os formatos esperados.

### Back-end

A API realiza uma nova validação dos dados recebidos antes de realizar a persistência no banco de dados.

Dessa forma, requisições inválidas também são rejeitadas quando enviadas diretamente por ferramentas como Postman, Insomnia ou `curl`.

## Status HTTP

A API utiliza códigos HTTP de acordo com o resultado das operações:

| Status            | Utilização                                    |
| ----------------- | --------------------------------------------- |
| `200 OK`          | Consulta realizada com sucesso                |
| `201 Created`     | Recurso criado com sucesso                    |
| `204 No Content`  | Operação concluída sem conteúdo para retornar |
| `400 Bad Request` | Dados enviados são inválidos                  |
| `404 Not Found`   | Recurso não encontrado                        |

## Banco de dados

Os dados dos Digimons são persistidos em um banco de dados relacional utilizando `JdbcTemplate`.

A configuração da conexão deve ser realizada no arquivo:

```text
digimon/src/main/resources/application.properties
```

O script SQL utilizado para criação das tabelas encontra-se no projeto.

## Objetivo acadêmico

O projeto tem como objetivo demonstrar a integração entre um front-end desenvolvido com tecnologias nativas e uma API REST desenvolvida em Spring Boot, aplicando:

* Consumo de API com `fetch()`;
* Manipulação do DOM com JavaScript;
* Validação de dados;
* Comunicação HTTP;
* Desenvolvimento de API REST;
* Persistência com `JdbcTemplate`;
* Utilização de banco de dados relacional;
* Tratamento de respostas HTTP;
* Configuração de CORS.
