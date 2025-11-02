-- -----------------------------------------------------------------------------
-- Alimenta o BD com cargos
-- -----------------------------------------------------------------------------
INSERT INTO CARGOS(nome)
VALUES ('USER'), ('ADMIN'), ('PROFESSOR'), ('COORDENADOR');

-- -----------------------------------------------------------------------------
-- Alimenta o BD com usuarios
-- -----------------------------------------------------------------------------
-- Usuarios
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Lucas Silva', 'lucas.silva', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'lucasmorais@1022.com', 1);
-- senha: ls123
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Ana Costa', 'ana.costa', '$2a$10$6Zms2qpyZzQgw3LVLYZVfeTCnRWYdxqDlzDv9/8.hGNr.cdBYrfqS', 'ana.costa@exemplo.com', 1);
-- senha: ac123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Carlos Santos', 'carlos.santos', '$2a$10$73pE/Izz/N5IBmb7Q4nItuv6XkmEMzDMQJyCD.cSU.FoXzNz2A.X6', 'carlos.santos@exemplo.com', 1);
-- senha: cs123

-- Administrador (usuários com cargo de Administrador)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Auxiliar Docente', 'auxiliar.docente', '$2a$10$U8M2J3A0TyA810ahFKIaEe0QPWI0DFH829sxA.hluSMXf7TjWTWDO', 'admin@fatec.sp.gov.br', 2);
-- senha: ad123

-- Professores (usuários com cargo de professor)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Sergio Salgado', 'sergio.salgado', '$2a$10$81xa53sYNSE/uKa5AmIZ.ORh.2V/HkCsUjaiVqyYC.f7iEB7keNWC', 'sergio.salgado@fatec.sp.gov.br', 3);
-- senha: ss123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Dimas Cardoso', 'dimas.cardoso', '$2a$10$WuLoYakqJG0h7wuJx8kW0OD89VFf6MUBv3zYjw3NKNjf1CwYGLvtK', 'dimas.cardoso@fatec.sp.gov.br', 3);
-- senha: dc123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Fabricio Londero', 'fabricio.londero', '$2a$10$OwAk2OjM1HZBNEHKfB2kAu7D4Mu43isMphmvVVoZGqXrDKkksLk0u', 'fabricio.londero@fatec.sp.gov.br', 3);
-- senha: fl123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Francisco Bianchi', 'francisco.bianchi', '$2a$10$qmciMRg4E9JbLc6vqI30zeBLBBoK8zfTjkI8dnknJOTL9Q92QnvSq', 'francisco.bianchi@fatec.sp.gov.br', 3);
-- senha: fb123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Luis dos Santos', 'luis.santos', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'luis.santos@fatec.sp.gov.br', 3);
-- senha: ls123

-- Coordenadores (usuários com cargo de coordenador)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Lucimar de Santi', 'lucimar.santi', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'lucimar.desanti@fatec.sp.gov.br', 4);
-- senha: ls123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Andre Olimpio', 'andre.olimpio', '$2a$10$VxhUleM0Ep5CCdCjffTBWeGD5jDWY8To/iyKKbt9gX.FFbl09rY8.', 'andre.olimpio@fatec.sp.gov.br', 4);
-- senha: ao123



-- -----------------------------------------------------------------------------
-- Alimenta o BD com as informações dos usuarios
-- -----------------------------------------------------------------------------
-- Professores
INSERT INTO PROFESSORES (user_id, registro_professor)
VALUES 
    (4, 1010),  -- Prof. Sergio Salgado
    (5, 1011),  -- Prof. Dimas Cardoso
    (6, 1012),  -- Prof. Fabricio Londero
    (7, 1013);  -- Prof. Francisco Bianchi


-- Coordenadores
INSERT INTO COORDENADORES (user_id, registro_coordenacao)
VALUES 
    (8, 2001),  -- Coord. Lucimar de Santi
    (9, 2002);  -- Coord. Andre Olimpio


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Cursos
-- -----------------------------------------------------------------------------
INSERT INTO CURSOS (nome_curso, coordenador_id)
VALUES 
    ('Análise e Desenvolvimento de Sistemas', 8),
    ('Gestão da Tecnologia da Informação', 9),
    ('Mecatrônica Industrial', 8);


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Disciplinas
-- -----------------------------------------------------------------------------
INSERT INTO DISCIPLINAS (nome, semestre, professor_id, curso_id)
VALUES 
    -- Curso: ADS (curso_id = 1)
    ('Engenharia de Software III', '2025.2', 4, 1),
    ('Laboratório de Banco de Dados', '2025.2', 6, 1),
    ('Programação Orientada à objetos', '2025.1', 5, 1),
    ('Segurança da Informação', '2025.1', 7, 1),
    
    -- Curso: GTI (curso_id = 2)
    ('Gestão de Projetos', '2024.2', 7, 2),
    ('Governança de TI', '2024.2', 6, 2),
    
    -- Curso: Mecatrônica Industrial (curso_id = 3)
    ('Eletromagnetismo', '2023.2', 7, 3),
    ('Cálculo', '2023.2', 6, 3);

    INSERT INTO TIPOSSALAS (nome)
    VALUES 
    ('Sala de Aula'),               
    ('Laboratório de Informática'), 
    ('Sala Maker'), 
    ('Laboratório de Mecatrônica');


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Salas
-- -----------------------------------------------------------------------------
-- CORRIGIDO: Adicionada a coluna 'observacoes' para combinar com os valores fornecidos
INSERT INTO SALAS (nome, capacidade, piso, disponibilidade, id_tipo_sala, observacoes)
VALUES 
    ('Lab 301', 30, 3, FALSE, 2, 'Laboratório com 30 computadores'),
    ('Lab 302', 25, 3, FALSE, 2, 'Laboratório de redes e infraestrutura'),
    ('Lab 303', 30, 3, FALSE, 2, 'Laboratório com computadores high-end'),
    ('Lab 304', 30, 3, FALSE, 2, 'Laboratório com 30 computadores'),
    ('Lab 305', 25, 3, FALSE, 2, 'Laboratório de redes e infraestrutura'),
    ('Lab 306', 30, 3, FALSE, 2, 'Laboratório com computadores high-end'),
    ('Sala 101', 40, 1, FALSE, 1, 'Sala com projetor e ar condicionado'),
    ('Sala 102', 40, 1, FALSE, 1, 'Sala com lousa digital'),
    ('Sala 201', 50, 2, FALSE, 1, 'Auditório pequeno'),
    ('Sala 202', 35, 2, FALSE, 2, 'Sala com projetor'),
    ('Sala 103', 40, 1, TRUE, 1, 'Sala de aula padrão com lousa branca'),
    ('Sala 104', 35, 1, TRUE, 1, 'Sala de aula padrão com lousa branca'), 
    ('Sala 203', 50, 2, TRUE, 1, 'Sala ampla com duas lousas');

-- TipoRecurso
INSERT INTO TIPO_RECURSO (NOME)
VALUES 
    ('Hardware'),
    ('Software'),
    ('Mobiliário');

-- Recursos
INSERT INTO RECURSOS (NOME, ID_TIPO_RECURSO) VALUES
    ('Webcam Logitech C920', 1),
    ('Monitor Dell UltraSharp 24"', 1),
    ('Impressora Multifuncional HP', 1),
    ('Teclado Mecânico ABNT2', 1),
    ('Licença Microsoft Project', 2),
    ('Licença Visual Studio Code', 2),
    ('Cadeira de Escritório Ergonômica', 3),
    ('Mesa de Trabalho com Gaveteiro', 3);

-- RecursosSalas
INSERT INTO RECURSOSSALAS (ID_SALA, ID_RECURSO, QUANTIDADE) VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 30),
    (4, 4, 30),
    (5, 5, 40),
    (6, 6, 35),
    (7, 7, 1),
    (8, 8, 25),
    (9, 1, 5),
    (10, 2, 1);

-- Janelas de Horário
INSERT INTO JANELAS_HORARIO (hora_inicio, hora_fim) VALUES
    ('07:40:00', '08:30:00'),
    ('09:10:00', '09:20:00'),
    ('09:30:00', '10:20:00'),
    ('10:20:00', '11:10:00'),
    ('11:20:00', '12:10:00'),
    ('12:10:00', '13:00:00'),
    ('13:20:00', '14:10:00'),
    ('14:10:00', '15:00:00'),
    ('15:10:00', '16:00:00'),
    ('16:00:00', '16:50:00'),
    ('19:00:00', '19:50:00'),
    ('19:50:00', '20:40:00'),
    ('20:50:00', '21:40:00'),
    ('21:40:00', '22:30:00'),
    ('23:00:00', '23:50:00');

INSERT INTO AGENDAMENTOS (user_id, sala_id, data_inicio, data_fim, dia_da_semana, janela_horario_id, tipo)
VALUES 
    -- Agendamentos de aulas
    (1, 1, '2025-10-20', '2025-12-15', 'Segunda-feira',1, 'AULA'),
    (1, 1, '2025-10-20', '2025-12-15', 'Quarta-feira',5, 'AULA'),
    (1, 5, '2025-10-20', '2025-12-15', 'Terça-feira',6, 'AULA'),
    (2, 2, '2025-10-20', '2025-12-15', 'Segunda-feira',2,'AULA'),
    (2, 2, '2025-10-20', '2025-12-15', 'Quinta-feira',3,'AULA'),
    (1, 5, '2025-10-20', '2025-12-15', 'Sexta-feira',1, 'AULA'),
    (3, 3, '2025-10-20', '2025-12-15', 'Quarta-feira',4,'AULA'),
    (3, 6, '2025-10-20', '2025-12-15', 'Terça-feira', 6, 'AULA'),
    (1, 7, '2025-10-20', '2025-12-15', 'Quinta-feira', 5, 'AULA'),
    (2, 4, '2025-10-20', '2025-12-15', 'Segunda-feira', 3, 'AULA'),
    
    -- Agendamentos de eventos
    (8, 3, '2025-11-15', '2025-11-15', 'Sexta-feira', 2, 'EVENTO'),
    (9, 3, '2025-11-22', '2025-11-22', 'Sexta-feira', 3,'EVENTO'),
    (10, 2, '2025-12-10', '2025-12-10', 'Terça-feira', 4,'EVENTO');

-- Relação agendamento-disciplina
INSERT INTO AGENDAMENTO_AULAS (agendamento_id, disciplina_id)
VALUES 
    (1, 1),   -- Engenharia de Software III - Segunda 08:00
    (2, 1),   -- Engenharia de Software III - Quarta 08:00
    (3, 4),   -- Segurança da Informação - Terça 10:00 (Lab)
    (4, 2),   -- Laboratório de Banco de Dados - Segunda 10:00
    (5, 2),   -- Laboratório de Banco de Dados - Quinta 10:00
    (6, 2),   -- Laboratório de Banco de Dados - Sexta 08:00 (Lab)
    (7, 3),   -- Programação Orientada à objetos - Quarta 14:00
    (8, 5),   -- Governança de TI - Terça 14:00 (Lab)
    (9, 8),   -- Cálculo - Quinta 14:00 (Lab)
    (10, 6);  -- Gestão de Projetos - Segunda 14:00


-- AgendamentoEventos
INSERT INTO AGENDAMENTO_EVENTOS (agendamento_id)
VALUES 
    (11),  -- Evento coordenação - 15/11
    (12),  -- Evento coordenação - 22/11
    (13);  -- Evento admin - 10/12

