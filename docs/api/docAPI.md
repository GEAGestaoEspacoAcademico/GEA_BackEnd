# 📚 Documentação da API - Sistema de Agendamento de Salas

## 🔗 Base URL
```
http://localhost:8080
```

---

## 📑 Índice
1. [Autenticação](#autenticação)
2. [Usuários](#usuários)
3. [Coordenadores](#coordenadores)
4. [Professores](#professores)
5. [Disciplinas](#disciplinas)
6. [Cursos](#cursos)
7. [Cargos](#cargos)
8. [Agendamentos de Aula](#agendamentos-de-aula)
9. [Agendamentos Gerais](#agendamentos-gerais)

---

## 🔐 Autenticação

### Registrar Novo Usuário
**POST** `/auth/register`

Cria um novo usuário no sistema com cargo padrão "USER".

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "login": "joaosilva",
  "senha": "senha123"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "cargoId": 1
}
```

**Possíveis Exceções:**
- `"CARGO USER NÃO ENCONTRADO"` - Cargo padrão não existe no banco de dados

---

### Login
**POST** `/auth/login`

Autentica um usuário no sistema.

**Request Body:**
```json
{
  "login": "joaosilva",
  "senha": "senha123"
}
```

**Response:** `200 OK`
```json
{
  "nome": "João Silva"
}
```

**Possíveis Exceções:**
- `"Erro ao validar senha"` - Login ou senha incorretos

---

## 👤 Usuários

### Listar Todos os Usuários
**GET** `/usuarios`

Retorna a lista de todos os usuários cadastrados.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "cargoId": 1
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "email": "maria.santos@example.com",
    "cargoId": 2
  }
]
```

---

### Buscar Usuário por ID
**GET** `/usuarios/{id}`

Retorna os dados de um usuário específico.

**Parâmetros de URL:**
- `id` (Long) - ID do usuário

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "cargoId": 1
}
```

**Possíveis Exceções:**
- `"Usuario não encontrado"` - ID inexistente

---

### Atualizar Usuário (Admin)
**PATCH** `/usuarios/{id}`

Atualiza os dados de um usuário. Endpoint para administradores.

**Parâmetros de URL:**
- `id` (Long) - ID do usuário

**Request Body:**
```json
{
  "nome": "João da Silva Santos",
  "email": "joao.novo@example.com",
  "cargoId": 2
}
```
*Todos os campos são opcionais*

**Response:** `204 No Content`

**Possíveis Exceções:**
- `"Tentando usar email já cadastrado"` - Email já está em uso por outro usuário
- `"Não encontrado cargo desejado"` - ID do cargo não existe

---

## 👨‍💼 Coordenadores

### Promover Usuário a Coordenador
**POST** `/coordenadores`

Promove um usuário existente para coordenador.

**Request Body:**
```json
{
  "usuarioId": 1,
  "registroCoordenacao": 12345
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "registroCoordenacao": 12345,
  "cargoId": 3
}
```

**Possíveis Exceções:**
- `"Registro de coordenação já existe"` - Registro de coordenação duplicado
- `"Usuário não encontrado"` - ID do usuário não existe
- `"Usuário já é coordenador"` - Usuário já foi promovido
- `"Cargo COORDENADOR não encontrado"` - Cargo não existe no sistema
- `"Falha ao promover coordenador"` - Erro na promoção

---

### Listar Todos os Coordenadores
**GET** `/coordenadores`

Retorna a lista de todos os coordenadores.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "registroCoordenacao": 12345,
    "cargoId": 3
  }
]
```

---

### Buscar Coordenador por ID
**GET** `/coordenadores/{id}`

Busca um coordenador pelo ID.

**Parâmetros de URL:**
- `id` (Long) - ID do coordenador

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "registroCoordenacao": 12345,
  "cargoId": 3
}
```

**Possíveis Exceções:**
- `"Coordenador não encontrado"` - ID não existe

---

### Buscar Coordenador por Registro
**GET** `/coordenadores/registro/{registro}`

Busca um coordenador pelo número de registro.

**Parâmetros de URL:**
- `registro` (int) - Número de registro do coordenador

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "registroCoordenacao": 12345,
  "cargoId": 3
}
```

**Possíveis Exceções:**
- `"Coordenador não encontrado"` - Registro não existe

---

### Despromover Coordenador
**DELETE** `/coordenadores/{id}`

Remove o cargo de coordenador de um usuário, voltando-o para cargo USER.

**Parâmetros de URL:**
- `id` (Long) - ID do coordenador

**Response:** `204 No Content`

**Possíveis Exceções:**
- `"Coordenador não encontrado"` - ID não existe
- `"Usuário não encontrado"` - Erro ao buscar usuário
- `"Cargo USER não encontrado"` - Cargo USER não existe no sistema

---

## 👨‍🏫 Professores

### Listar Todos os Professores
**GET** `/professores/listar`

Retorna a lista de todos os professores.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Prof. Carlos Eduardo",
    "email": "carlos@example.com",
    "registroProfessor": 98765,
    "cargoId": 4
  }
]
```

---

### Excluir Professor
**DELETE** `/professores/{registroProfessor}`

Exclui um professor pelo número de registro.

**Parâmetros de URL:**
- `registroProfessor` (long) - Registro do professor

**Response:** `204 No Content`

**Possíveis Exceções:**
- `"Professor com Registro {registroProfessor} não encontrado."` - Registro não existe

---

### Editar Professor
**PUT** `/professores/{id}`

Edita as informações de um professor.

**Response:** `200 OK`
```json
[
  {
    "id": 5,
    "nome": "Prof. Sergio Salgado",
    "email": "sergio.salgado@fatec.sp.gov.br",
    "registroProfessor": 1010,
    "cargoId": 3
  }
]
```

## 📖 Disciplinas

### Criar Disciplina
**POST** `/disciplinas`

Cria uma nova disciplina.

**Request Body:**
```json
{
  "nome": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "professor": {
    "id": 1
  },
  "curso": {
    "id": 1
  }
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "professor": {
    "id": 1,
    "nome": "Prof. Carlos Eduardo"
  },
  "curso": {
    "id": 1,
    "nomeCurso": "Análise e Desenvolvimento de Sistemas"
  }
}
```

---

### Listar Todas as Disciplinas
**GET** `/disciplinas`

Retorna a lista de todas as disciplinas.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Programação Orientada a Objetos",
    "semestre": "2024/1",
    "professor": {
      "id": 1,
      "nome": "Prof. Carlos Eduardo"
    },
    "curso": {
      "id": 1,
      "nomeCurso": "Análise e Desenvolvimento de Sistemas"
    }
  }
]
```

---

### Buscar Disciplina por ID
**GET** `/disciplinas/{id}`

Busca uma disciplina específica.

**Parâmetros de URL:**
- `id` (Integer) - ID da disciplina

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "professor": {
    "id": 1,
    "nome": "Prof. Carlos Eduardo"
  },
  "curso": {
    "id": 1,
    "nomeCurso": "Análise e Desenvolvimento de Sistemas"
  }
}
```

**Possíveis Exceções:**
- `"Disciplina não encontrada"` - ID não existe

---

### Atualizar Disciplina
**PUT** `/disciplinas/{id}`

Atualiza os dados de uma disciplina.

**Parâmetros de URL:**
- `id` (Integer) - ID da disciplina

**Request Body:**
```json
{
  "nome": "Programação Orientada a Objetos II",
  "semestre": "2024/2",
  "professor": {
    "id": 2
  },
  "curso": {
    "id": 1
  }
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "Programação Orientada a Objetos II",
  "semestre": "2024/2",
  "professor": {
    "id": 2
  },
  "curso": {
    "id": 1
  }
}
```

**Possíveis Exceções:**
- `"Disciplina não encontrada"` - ID não existe

---

### Excluir Disciplina
**DELETE** `/disciplinas/{id}`

Exclui uma disciplina.

**Parâmetros de URL:**
- `id` (Integer) - ID da disciplina

**Response:** `200 OK`

**Possíveis Exceções:**
- `"Disciplina não encontrada"` - ID não existe

---

## 🎓 Cursos

### Criar Curso
**POST** `/cursos`

Cria um novo curso.

**Request Body:**
```json
{
  "nomeCurso": "Análise e Desenvolvimento de Sistemas",
  "coordenador": {
    "id": 1
  }
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nomeCurso": "Análise e Desenvolvimento de Sistemas",
  "coordenador": {
    "id": 1,
    "nome": "João Silva"
  }
}
```

---

### Listar Todos os Cursos
**GET** `/cursos`

Retorna a lista de todos os cursos.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nomeCurso": "Análise e Desenvolvimento de Sistemas",
    "coordenador": {
      "id": 1,
      "nome": "João Silva"
    }
  }
]
```

---

### Buscar Curso por ID
**GET** `/cursos/{id}`

Busca um curso específico.

**Parâmetros de URL:**
- `id` (Long) - ID do curso

**Response:** `200 OK`
```json
{
  "id": 1,
  "nomeCurso": "Análise e Desenvolvimento de Sistemas",
  "coordenador": {
    "id": 1,
    "nome": "João Silva"
  }
}
```

**Possíveis Exceções:**
- `"Curso não encontrado. Id={id}"` - ID não existe

---

### Atualizar Curso
**PUT** `/cursos/{id}`

Atualiza os dados de um curso.

**Parâmetros de URL:**
- `id` (Long) - ID do curso

**Request Body:**
```json
{
  "nomeCurso": "Análise e Desenvolvimento de Sistemas - Noturno",
  "coordenador": {
    "id": 2
  }
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nomeCurso": "Análise e Desenvolvimento de Sistemas - Noturno",
  "coordenador": {
    "id": 2
  }
}
```

**Possíveis Exceções:**
- `"Curso não encontrado. Id={id}"` - ID não existe

---

### Excluir Curso
**DELETE** `/cursos/{id}`

Exclui um curso.

**Parâmetros de URL:**
- `id` (Long) - ID do curso

**Response:** `200 OK`

**Possíveis Exceções:**
- `"Curso não encontrado. Id={id}"` - ID não existe

---

## 🎭 Cargos

### Listar Todos os Cargos
**GET** `/cargos`

Retorna a lista de todos os cargos disponíveis.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "USER"
  },
  {
    "id": 2,
    "nome": "ADMIN"
  },
  {
    "id": 3,
    "nome": "COORDENADOR"
  }
]
```

---

### Cadastrar Cargo
**POST** `/cargos`

Cria um novo cargo no sistema.

**Request Body:**
```json
{
  "nome": "PROFESSOR"
}
```

**Response:** `201 Created`
```json
{
  "id": 4,
  "nome": "PROFESSOR"
}
```

**Possíveis Exceções:**
- `"Impossivel cadastrar cargo, pois já existe com esse nome"` - Nome duplicado

---

## 📅 Agendamentos de Aula

### Criar Agendamento de Aula
**POST** `/agendamentos/aulas`

Cria um novo agendamento de aula.

**Request Body:**
```json
{
  "usuarioId": 1,
  "salaId": 1,
  "disciplinaId": 1,
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-30",
  "diaDaSemana": "Segunda-feira",
  "horaInicio": "08:00:00",
  "horaFim": "10:00:00",
  "tipo": "Aula"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nomeUsuario": "João Silva",
  "nomeSala": "Sala 101",
  "disciplinaId": 1,
  "nomeDisciplina": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "curso": "Análise e Desenvolvimento de Sistemas",
  "nomeProfessor": "Prof. Carlos Eduardo",
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-30",
  "diaDaSemana": "Segunda-feira",
  "horaInicio": "08:00:00",
  "horaFim": "10:00:00",
  "tipo": "Aula"
}
```

**Possíveis Exceções:**
- `"Usuário não encontrado com ID: {id}"` - ID do usuário não existe
- `"Sala não encontrada com ID: {id}"` - ID da sala não existe
- `"Disciplina não encontrada com ID: {id}"` - ID da disciplina não existe

---

### Listar Todos os Agendamentos de Aula
**GET** `/agendamentos/aulas`

Retorna a lista de todos os agendamentos de aula.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nomeUsuario": "João Silva",
    "nomeSala": "Sala 101",
    "disciplinaId": 1,
    "nomeDisciplina": "Programação Orientada a Objetos",
    "semestre": "2024/1",
    "curso": "Análise e Desenvolvimento de Sistemas",
    "nomeProfessor": "Prof. Carlos Eduardo",
    "dataInicio": "2024-03-01",
    "dataFim": "2024-06-30",
    "diaDaSemana": "Segunda-feira",
    "horaInicio": "08:00:00",
    "horaFim": "10:00:00",
    "tipo": "Aula"
  }
]
```

---

### Buscar Agendamento de Aula por ID
**GET** `/agendamentos/aulas/{id}`

Busca um agendamento específico.

**Parâmetros de URL:**
- `id` (Long) - ID do agendamento

**Response:** `200 OK`
```json
{
  "id": 1,
  "nomeUsuario": "João Silva",
  "nomeSala": "Sala 101",
  "disciplinaId": 1,
  "nomeDisciplina": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "curso": "Análise e Desenvolvimento de Sistemas",
  "nomeProfessor": "Prof. Carlos Eduardo",
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-30",
  "diaDaSemana": "Segunda-feira",
  "horaInicio": "08:00:00",
  "horaFim": "10:00:00",
  "tipo": "Aula"
}
```

**Possíveis Exceções:**
- `"Agendamento de aula não encontrado com ID: {id}"` - ID não existe

---

### Buscar Agendamentos por Disciplina
**GET** `/agendamentos/aulas/disciplina/{disciplinaId}`

Retorna todos os agendamentos de uma disciplina específica.

**Parâmetros de URL:**
- `disciplinaId` (Integer) - ID da disciplina

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nomeUsuario": "João Silva",
    "nomeSala": "Sala 101",
    "disciplinaId": 1,
    "nomeDisciplina": "Programação Orientada a Objetos",
    "semestre": "2024/1",
    "curso": "Análise e Desenvolvimento de Sistemas",
    "nomeProfessor": "Prof. Carlos Eduardo",
    "dataInicio": "2024-03-01",
    "dataFim": "2024-06-30",
    "diaDaSemana": "Segunda-feira",
    "horaInicio": "08:00:00",
    "horaFim": "10:00:00",
    "tipo": "Aula"
  }
]
```

---

### Buscar Agendamentos por Professor
**GET** `/agendamentos/aulas/professor/{professorId}`

Retorna todos os agendamentos de um professor específico.

**Parâmetros de URL:**
- `professorId` (Integer) - ID do professor

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nomeUsuario": "João Silva",
    "nomeSala": "Sala 101",
    "disciplinaId": 1,
    "nomeDisciplina": "Programação Orientada a Objetos",
    "semestre": "2024/1",
    "curso": "Análise e Desenvolvimento de Sistemas",
    "nomeProfessor": "Prof. Carlos Eduardo",
    "dataInicio": "2024-03-01",
    "dataFim": "2024-06-30",
    "diaDaSemana": "Segunda-feira",
    "horaInicio": "08:00:00",
    "horaFim": "10:00:00",
    "tipo": "Aula"
  }
]
```

---

### Atualizar Agendamento de Aula
**PUT** `/agendamentos/aulas/{id}`

Atualiza um agendamento de aula existente.

**Parâmetros de URL:**
- `id` (Long) - ID do agendamento

**Request Body:**
```json
{
  "usuarioId": 1,
  "salaId": 2,
  "disciplinaId": 1,
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-30",
  "diaDaSemana": "Terça-feira",
  "horaInicio": "10:00:00",
  "horaFim": "12:00:00",
  "tipo": "Aula"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nomeUsuario": "João Silva",
  "nomeSala": "Sala 102",
  "disciplinaId": 1,
  "nomeDisciplina": "Programação Orientada a Objetos",
  "semestre": "2024/1",
  "curso": "Análise e Desenvolvimento de Sistemas",
  "nomeProfessor": "Prof. Carlos Eduardo",
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-30",
  "diaDaSemana": "Terça-feira",
  "horaInicio": "10:00:00",
  "horaFim": "12:00:00",
  "tipo": "Aula"
}
```

**Possíveis Exceções:**
- `"Agendamento de aula não encontrado com ID: {id}"` - ID não existe

---

### Excluir Agendamento de Aula
**DELETE** `/agendamentos/aulas/{id}`

Exclui um agendamento de aula.

**Parâmetros de URL:**
- `id` (Long) - ID do agendamento

**Response:** `204 No Content`

**Possíveis Exceções:**
- `"Agendamento de aula não encontrado com ID: {id}"` - ID não existe

---

## 📋 Agendamentos Gerais

### Listar Todos os Agendamentos
**GET** `/agendamentos`

Retorna a lista de todos os agendamentos (inclui aulas e eventos).

**Response:** `200 OK`
```json
[
  {
    "nomeUsuario": "João Silva",
    "dataInicio": "2024-03-01",
    "dataFim": "2024-06-30",
    "diaDaSemana": "Segunda-feira",
    "horaInicio": "08:00:00",
    "horaFim": "10:00:00",
    "tipo": "Aula"
  },
  {
    "nomeUsuario": "Maria Santos",
    "dataInicio": "2024-03-15",
    "dataFim": "2024-03-15",
    "diaDaSemana": "Sexta-feira",
    "horaInicio": "14:00:00",
    "horaFim": "18:00:00",
    "tipo": "Evento"
  }
]
```


---

**Última Atualização:** 22 de outubro de 2025  
**Versão da API:** 1.0
