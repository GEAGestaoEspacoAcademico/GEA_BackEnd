# GEA: Sistema de Gestão de Espaço Acadêmico

O **GEA_BackEnd** é o backend responsável por gerenciar a alocação e agendamento de espaços acadêmicos, como salas de aula e laboratórios, em instituições de ensino. Desenvolvido com **Spring Boot**, ele fornece uma API robusta e segura para o controle de recursos, usuários, cursos, disciplinas e agendamentos.

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias e ferramentas:

| Categoria | Tecnologia |
| :--- | :--- |
| **Linguagem** | Java |
| **Framework** | Spring Boot |
| **Persistência** | Spring Data JPA |
| **Banco de Dados** | H2 Database (Desenvolvimento) |
| **Segurança** | Spring Security |
| **Documentação API** | SpringDoc OpenAPI (Swagger UI) |
| **Gerenciador de Dependências** | Maven |
| **Auxiliar** | Lombok |

## 📦 Estrutura do Projeto

O projeto segue a estrutura padrão de um aplicativo Spring Boot, organizado em pacotes que refletem a arquitetura de camadas:

```
GEA_BackEnd/
├── src/main/java/com/fatec.itu.agendasalas/
│   ├── config/             # Configurações de segurança e OpenAPI
│   ├── controllers/        # Camada de controle 
│   ├── dto/                # Objetos de Transferência de Dados 
│   ├── entity/             # Entidades de persistência 
│   ├── exceptions/         # Classes de exceção personalizadas
│   ├── repositories/       # Repositórios para acesso a dados 
│   └── services/           # Camada de serviço 
├── src/main/resources/
│   ├── application.properties # Configurações do ambiente
│   └── data.sql               # Script de inicialização de dados 
└── pom.xml                 # Arquivo de configuração do Maven
```

## ✨ Funcionalidades Principais

O sistema GEA_BackEnd gerencia as seguintes entidades e processos:

| Módulo | Descrição |
| :--- | :--- |
| **Autenticação e Usuários** | Gerenciamento de usuários (`Usuario`) e cargos (`Cargo`), com autenticação segura via Spring Security. |
| **Agendamentos** | Criação e gestão de agendamentos de aulas (`AgendamentoAula`) e eventos (`AgendamentoEvento`). |
| **Salas e Recursos** | Cadastro de salas (`Sala`), tipos de sala (`TipoSala`), recursos (`Recurso`) e tipos de recurso (`TipoRecurso`). |
| **Estrutura Acadêmica** | Cadastro de professores (`Professor`), coordenadores (`Coordenador`), cursos (`Curso`) e disciplinas (`Disciplina`). |
| **Janelas de Horário** | Definição de janelas de horário (`JanelasHorario`) disponíveis para agendamento. |
| **Notificações** | Sistema de notificação (`Notificacao`) para alertar sobre agendamentos e alterações. |

## 🛠️ Configuração e Execução

### Pré-requisitos

Para executar o projeto localmente, você precisará ter instalado:

*   **Java Development Kit (JDK)**: Versão 17 ou superior.
*   **Maven**: Para gerenciamento de dependências e construção do projeto.

### 1. Clonar o Repositório

```bash
git clone https://github.com/GEAGestaoEspacoAcademico/GEA_BackEnd.git
cd GEA_BackEnd
```

### 2. Construir e Executar o Projeto

Utilize o Maven para construir e executar o aplicativo Spring Boot:

```bash
# Compila o projeto e empacota em um JAR
mvn clean install

# Executa o aplicativo
mvn spring-boot:run
```

A API estará acessível em `http://localhost:8080`.

## 📄 Documentação da API (Swagger UI)

Após a execução do projeto, a documentação interativa da API estará disponível através do Swagger UI.

Acesse a URL:

```
http://localhost:8080/swagger-ui.html
```

Você poderá visualizar todos os endpoints, modelos de dados (DTOs) e testar as requisições diretamente no navegador.
