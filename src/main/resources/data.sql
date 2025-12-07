-- -----------------------------------------------------------------------------
-- Alimenta o BD com cargos
-- -----------------------------------------------------------------------------
INSERT INTO CARGOS(nome)
VALUES ('USER'), ('AUXILIAR_DOCENTE'), ('PROFESSOR'), ('COORDENADOR'), ('SECRETARIA');

-- -----------------------------------------------------------------------------
-- Alimenta o BD com usuarios
-- -----------------------------------------------------------------------------
-- Usuarios
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Lucas Silva', 'lucas.silva', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'lucas1.morais26@fatec.sp.gov.br', 1);
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

INSERT INTO AUXILIAR_DOCENTES (area, user_id)
VALUES
('MECATRONICA', 4);

-- Professores (usuários com cargo de professor)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Sergio Salgado', 'sergio.salgado', '$2a$10$81xa53sYNSE/uKa5AmIZ.ORh.2V/HkCsUjaiVqyYC.f7iEB7keNWC', 'lucas.morais26@fatec.sp.gov.br', 3);
-- senha: ss123
    
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Dimas Cardoso', 'dimas.cardoso', '$2a$10$WuLoYakqJG0h7wuJx8kW0OD89VFf6MUBv3zYjw3NKNjf1CwYGLvtK', 'dimas.cardoso0@fatec.sp.gov.br', 3);
-- senha: dc123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Fabricio Londero', 'fabricio.londero', '$2a$10$OwAk2OjM1HZBNEHKfB2kAu7D4Mu43isMphmvVVoZGqXrDKkksLk0u', 'fabricio.londero0@fatec.sp.gov.br', 3);
-- senha: fl123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Francisco Bianchi', 'francisco.bianchi', '$2a$10$qmciMRg4E9JbLc6vqI30zeBLBBoK8zfTjkI8dnknJOTL9Q92QnvSq', 'francisco.bianchi0@fatec.sp.gov.br', 3);
-- senha: fb123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Luis dos Santos', 'luis.santos', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'luis.santos0@fatec.sp.gov.br', 3);
-- senha: ls123

-- Coordenadores (usuários com cargo de coordenador)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Lucimar de Santi', 'lucimar.santi', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'lucimar.desanti0@fatec.sp.gov.br', 4);
-- senha: ls123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Andre Olimpio', 'andre.olimpio', '$2a$10$VxhUleM0Ep5CCdCjffTBWeGD5jDWY8To/iyKKbt9gX.FFbl09rY8.', 'andre.olimpio0@fatec.sp.gov.br', 4);
-- senha: ao123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Juliana Lima', 'juliana.lima', '$2b$10$17RkgaoI/nIF7iTOwaKFP.i7welZIKTNM9w7Wo.tCAT7l14ePJ5Pq', 'juliana.lima0@fatec.sp.gov.br', 4);
-- senha: jl123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Jose Henrique', 'jose.henrique', '$2b$10$a6lEHhhCB5w.QD6oFNGhQOLAUSL.blqLEAGkyQhjz/6Lrfm1AlZn2', 'jose.henrique0@fatec.sp.gov.br', 4);
-- senha: jh123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Rosa Marciani', 'rosa.marciani', '$2b$10$SWvYMaztqjkqswabJ4L9wO9JB2hy8R4JuJ.Nknm5nV2rQb42.813.', 'rosa.marciani0@fatec.sp.gov.br', 4);
-- senha: rm123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Paulo Macedo', 'paulo.macedo', '$2b$10$LIgfzaWEAgIxvr8DyLjQZuhMJb/jgd74mEnPTpcr7l/pm8rbhsPVq', 'paulo.macedo0@fatec.sp.gov.br', 4);
-- senha: pm123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Patricia Silva', 'patricia.silva', '$2b$10$DT.B5Udk.4xjq/x4Ky2s.u69mbqvcrhAGoRClQxpEZlpSWLaYEeRa', 'patricia.silva0@fatec.sp.gov.br', 4);
-- senha: ps123

-- Adição de Professores (usuários com cargo de professor)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Glauco Todesco', 'glauco.todesco', '$2b$10$WvG0kVJflyTS92VSf3Y/Nul8hIjDbwZw.b.CX4ZDmtqMKEN8VQd8O', 'glauco.todesco0@fatec.sp.gov.br', 3);
-- senha: gt123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Onei Junior', 'onei.junior', '$2b$10$uX8iarxMfrxrX098ycfld.29ARd7ZRpAerebW75yogzPp/tarHUyO', 'onei.junior0@fatec.sp.gov.br', 3);
-- senha: oj123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Marcos Araujo', 'marcos.araujo', '$2b$10$3NkwFH4IqK4vrw.GYFlXaurC0vas6m.xWzpH/tydelk9tTaFKu8Zi', 'marcos.araujo0@fatec.sp.gov.br', 3);
-- senha: ma123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Sergio Clauss', 'sergio.clauss', '$2b$10$KwNe5kvEl92Wn.TMAv4LeeZSmRAnrcwzYRaGx6OWUc7EyFG6sTf/a', 'sergio.clauss0@fatec.sp.gov.br', 3);
-- senha: sc123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Marcio Camargo', 'marcio.camargo', '$2b$10$59b6RtfU3fl0rY7FtBIGz.8P0xhlL3OSi6WcmzJYGoPBMrvj60/ii', 'marcio.camargo0@fatec.sp.gov.br', 3);
-- senha: mc123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Bruno Henrique', 'bruno.henrique', '$2b$10$XuQ/0chQkfKsHeRJchQfmeMXV7LXVKX2aPIH4vkTixzSf4dt3XcrK', 'bruno.henrique0@fatec.sp.gov.br', 3);
-- senha: bh123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Andre Egreggio', 'andre.egreggio', '$2b$10$aZHG/0z3dtSrbdqwbsN20Ok8lZYfbmRjwNHGbUE0HTjEwYuQqXOhO', 'andre.egreggio0@fatec.sp.gov.br', 3);
-- senha: ae123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Ricardo Leme', 'ricardo.leme', '$2b$10$8lOnaLNjfjBs7PdHSQIjm.yfEHG8ao2fGjEjxHHqjCZNlSIGzwz42', 'ricardo.leme0@fatec.sp.gov.br', 3);
-- senha: rl123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Prof. Levi Munhoz', 'levi.munhoz', '$2b$10$KzspyFMadrJvkxkK5kfWju0woWOWcEzWAcNlyD/.fYNH0Z3taCqpq', 'levi.munhoz0@fatec.sp.gov.br', 3);
-- senha: lm123

-- Adição de Alunos (usuários com cargo de aluno)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Caio Mortal', 'caio.mortal', '$2a$10$yQxY2f/Ti2kgkjB7F5Jw8Olq4r8yx2aXrYz6IVs9V/owC5Z3bMZcC', 'caio.mortal@exemplo.com', 1);
-- senha: cm123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Jenivan Silva', 'jenivan.silva', '$2a$10$C4t7S0Vb9FnpK9kTHe2vqe0mN9m8jXcPv3/1GhJw3wUOZ4ypx2eKq', 'jenivan.silva@exemplo.com', 1);
-- senha: js123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Lucas Guerra', 'lucas.guerra', '$2a$10$8v2TbFQh0kN7V0qZqP6xH.6X/2KNcYQ8lD4/YcWx4fHhKJ9tWzGHy', 'lucas.guerra@exemplo.com', 1);
-- senha: lg123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Abner Renner', 'abner.renner', '$2a$10$Wb6LqYz2qH9T/1V4c0vP2u8k3sN4hLxjB5vYfM1OeF3pQ9xT1cHaC', 'abner.renner@exemplo.com', 1);
-- senha: ar123

-- Adição de secretários(as)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Maria Oliveira', 'maria.oliveira', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'maria.oliveira@fatec.sp.gov.br', 5);
-- senha: ls123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Beatriz Pereira', 'beatriz.pereira', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'beatriz.pereira@fatec.sp.gov.br', 5);
-- senha: ls123

INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('João Almeida', 'joao.almeida', '$2a$10$UgpbUJfSlLwULfWnaBU3QusO78ip3iuq5uQWBiR08wrJFXvZIh.R2', 'joao.almeida@fatec.sp.gov.br', 5);
-- senha: ls123

INSERT INTO USUARIOS(nome, login, senha, email, cargo_id)
VALUES
('Jessica Carolina', 'jessica.carolina', '$2a$12$4KOlz6F7h1UkBZDCgtq8f.oY5tPM0GrL9x2.bVo44vkTj6OjJ2Bb.', 'jessica.carolina@fatc.sp.gov.br', 5);

INSERT INTO SECRETARIA(user_id, matricula)
VALUES
(30, 101000),
(31, 101001),
(32, 101002),
(33, 101003);


INSERT INTO SEMESTRES (nome) VALUES ('1º semestre');
INSERT INTO SEMESTRES (nome) VALUES ('2º semestre');
INSERT INTO SEMESTRES (nome) VALUES ('3º semestre');
INSERT INTO SEMESTRES (nome) VALUES ('4º semestre');
INSERT INTO SEMESTRES (nome) VALUES ('5º semestre');
INSERT INTO SEMESTRES (nome) VALUES ('6º semestre');


-- -----------------------------------------------------------------------------
-- Alimenta o BD com as informações dos usuarios
-- -----------------------------------------------------------------------------
-- Professores
INSERT INTO PROFESSORES (user_id, registro_professor)
VALUES 
    (5, 1010),   -- Prof. Sergio Salgado
    (6, 1011),   -- Prof. Dimas Cardoso
    (7, 1012),   -- Prof. Fabricio Londero
    (8, 1013),   -- Prof. Francisco Bianchi
    (9, 1014),   -- Prof. Luis dos Santos
    (17, 1015),  -- Prof. Glauco Todesco
    (18, 1016),  -- Prof. Onei Junior
    (19, 1017),  -- Prof. Marcos Araujo
    (20, 1018),  -- Prof. Sergio Clauss
    (21, 1019),  -- Prof. Marcio Camargo
    (22, 1020),  -- Prof. Bruno Henrique
    (23, 1021),  -- Prof. Andre Egreggio
    (24, 1022),  -- Prof. Ricardo Leme
    (25, 1023);  -- Prof. Levi Munhoz


-- Coordenadores
INSERT INTO COORDENADORES (user_id, registro_coordenacao)
VALUES 
    (10, 2001),  -- Coord. Lucimar de Santi
    (11, 2002),  -- Coord. Andre Olimpio
    (12, 2003),  -- Coord. Juliana Lima
    (13, 2004),  -- Coord. Jose Henrique
    (14, 2005),  -- Coord. Rosa Marciani
    (15, 2006),  -- Coord. Paulo Macedo
    (16, 2007);  -- Coord. Patricia Silva     


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Cursos
-- -----------------------------------------------------------------------------
INSERT INTO CURSOS (nome_curso, sigla, coordenador_id, excluido)
VALUES 
    ('Análise e Desenvolvimento de Sistemas', 'ADS', 10, FALSE),
    ('Gestão da Tecnologia da Informação', 'GTI', 13, FALSE),
    ('Mecatrônica Industrial', 'MEC', 11, FALSE),
    ('Eventos', 'EVE', 12, FALSE),
    ('Gestão Empresarial', 'GE', 14, FALSE),
    ('Gestão Empresarial EAD', 'GE-EAD', 15, FALSE),
    ('AMS Processos Gerenciais', 'AMS-PG', 16, FALSE);

-- -----------------------------------------------------------------------------
-- Alimenta o BD com Disciplinas
-- -----------------------------------------------------------------------------

-- Disciplinas: Curso ADS (curso_id = 1)
INSERT INTO DISCIPLINAS (nome, semestre_id, professor_id, curso_id, excluida) VALUES
    ('Engenharia de Software III', 2, 5, 1, FALSE),
    ('Laboratório de Banco de Dados', 2, 7, 1, FALSE),
    ('Programação Orientada à Objetos', 1, 6, 1, FALSE),
    ('Segurança da Informação', 1, 8, 1, FALSE),
    ('Sistemas de Informação', 1, 9, 1, FALSE),
    ('Algoritmos', 1, 17, 1, FALSE),
    ('Programação Web', 1, 17, 1, FALSE),
    ('Arq e Organização de Computadores', 1, 18, 1, FALSE),
    ('Administração Geral', 1, 19, 1, FALSE),
    ('Programação em Microinformática', 1, 20, 1, FALSE),
    ('Comunicação e Expressão', 1, 21, 1, FALSE),
    ('Matemática Discreta', 1, 22, 1, FALSE),
    ('Banco de Dados', 2, 24, 1, FALSE),
    ('Contabilidade', 2, 23, 1, FALSE),
    ('Engenharia de Software I', 2, 25, 1, FALSE),
    ('Linguagem de Programação', 2, 24, 1, FALSE);



INSERT INTO DISCIPLINAS (nome, semestre_id, professor_id, curso_id, excluida) VALUES
    ('Gestão de Projetos', 2, 8, 2, FALSE),
    ('Governança de TI', 2, 7, 2, FALSE),
    ('Processos Gerenciais', 2, 25, 2, FALSE),
    ('Gestão de Sistemas Operacionais', 2, 17, 2, FALSE),
    ('Matemática Financeira', 1, 21, 2, FALSE),
    ('Modelagem de Processos', 2, 20, 2, FALSE),
    ('Gestão de Pessoas', 2, 18, 2, FALSE);



INSERT INTO DISCIPLINAS (nome, semestre_id, professor_id, curso_id, excluida) VALUES
    ('Eletromagnetismo', 2, 8, 3, FALSE),
    ('Cálculo', 2, 7, 3, FALSE),
    ('Comunicação Acadêmica', 1, 9, 3, FALSE),
    ('Sist. Eletroeletrônicos Aplicados I', 2, 21, 3, FALSE),
    ('Int. Sistemas Dimensionais', 1, 25, 3, FALSE),
    ('Estatística Descritiva', 2, 5, 3, FALSE),
    ('Desenho Técnico', 2, 23, 3, FALSE);



    INSERT INTO TIPOS_SALAS (nome)
    VALUES 
    ('Sala de Aula'),               
    ('Laboratório de Informática'), 
    ('Sala Maker'), 
    ('Laboratório de Mecatrônica');


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Andares
-- -----------------------------------------------------------------------------
INSERT INTO PISOS (nome)
VALUES 
    ('Térreo'),
    ('1º Andar'),
    ('2º Andar');

    
-- -----------------------------------------------------------------------------
-- Alimenta o BD com Salas
-- -----------------------------------------------------------------------------
-- CORRIGIDO: Adicionada a coluna 'observacoes' para combinar com os valores fornecidos
INSERT INTO SALAS (nome, capacidade, disponibilidade, id_tipo_sala, piso_id, observacoes)
VALUES 
    ('Lab 301', 30, FALSE, 2, 3, 'Laboratório com 30 computadores'),
    ('Lab 302', 25, FALSE, 2, 3, 'Laboratório de redes e infraestrutura'),
    ('Lab 303', 30, FALSE, 2, 3, 'Laboratório com computadores high-end'),
    ('Lab 304', 30, FALSE, 2, 3, 'Laboratório com 30 computadores'),
    ('Lab 305', 25, FALSE, 2, 3, 'Laboratório de redes e infraestrutura'),
    ('Lab 306', 30, FALSE, 2, 3, 'Laboratório com computadores high-end'),
    ('Sala 101', 40, FALSE, 1, 1, 'Sala com projetor e ar condicionado'),
    ('Sala 102', 40, FALSE, 1, 1, 'Sala com lousa digital'),
    ('Sala 201', 50, FALSE, 1, 2, 'Auditório pequeno'),
    ('Sala 202', 35, FALSE, 2, 2, 'Sala com projetor'),
    ('Sala 103', 40, TRUE, 1, 1, 'Sala de aula padrão com lousa branca'),
    ('Sala 104', 35, TRUE, 1, 1, 'Sala de aula padrão com lousa branca'), 
    ('Sala 203', 50, TRUE, 1, 2, 'Sala ampla com duas lousas');


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Recursos
-- -----------------------------------------------------------------------------
-- TipoRecurso
INSERT INTO TIPO_RECURSO (NOME)
VALUES 
    ('Hardware'),
    ('Software'),
    ('Mobiliário');

-- Recursos
INSERT INTO RECURSOS (NOME, tipo_recurso_id) VALUES
    ('Webcam Logitech C920', 1),
    ('Monitor Dell UltraSharp 24"', 1),
    ('Impressora Multifuncional HP', 1),
    ('Teclado Mecânico ABNT2', 1),
    ('Licença Microsoft Project', 2),
    ('Licença Visual Studio Code', 2),
    ('Cadeira de Escritório Ergonômica', 3),
    ('Mesa de Trabalho com Gaveteiro', 3),
    ('Projetor', 1),
    ('Televisão LG 50', 1),
    ('Quadro Branco', 3);

-- RecursosSalas
INSERT INTO RECURSOS_SALAS (SALA_ID, RECURSO_ID, QUANTIDADE) VALUES
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

-- -----------------------------------------------------------------------------
-- Alimenta o BD com Janelas de Horário
-- -----------------------------------------------------------------------------
INSERT INTO JANELAS_HORARIO (hora_inicio, hora_fim) VALUES
    ('07:40:00', '09:20:00'),
    ('09:30:00', '11:20:00'),
    ('11:30:00', '13:00:00'),
    ('13:20:00', '15:00:00'),
    ('15:10:00', '16:50:00'),
    ('17:00:00', '18:40:00'),
    ('19:00:00', '20:40:00'),
    ('21:00:00', '22:40:00');



    INSERT INTO RECORRENCIA (data_inicio, data_fim)
    VALUES
    ('2025-12-15', '2025-12-15'),
    ('2025-12-15', '2025-12-15');

-- -----------------------------------------------------------------------------
-- Alimenta o BD com Agendamentos
-- -----------------------------------------------------------------------------
INSERT INTO AGENDAMENTOS 
(user_id, sala_id, data, dia_da_semana, janela_horario_id, is_evento, recorrencia_id, status, solicitante)
VALUES 
    -- Agendamentos de aulas
    (1, 1, '2025-12-15', 'Segunda-feira', 1, FALSE, 1, 'ATIVO', 'Sistema'),
    (1, 1, '2025-12-15', 'Quarta-feira', 5, FALSE, 1, 'ATIVO', 'Sistema'),
    (1, 5, '2025-12-15', 'Terça-feira', 6, FALSE, 1, 'ATIVO', 'Sistema'),
    (2, 2, '2025-12-15', 'Segunda-feira', 2, FALSE, 1, 'ATIVO', 'Sistema'),
    (2, 2, '2025-12-15', 'Quinta-feira', 3, FALSE, 1, 'ATIVO', 'Sistema'),
    (1, 5, '2025-12-15', 'Sexta-feira', 1, FALSE, 1, 'ATIVO', 'Sistema'),
    (3, 3, '2025-12-15', 'Quarta-feira', 4, FALSE, 1, 'ATIVO', 'Sistema'),
    (3, 6, '2025-12-15', 'Terça-feira', 6, FALSE, 2, 'ATIVO', 'Sistema'),
    (1, 7, '2025-12-15', 'Quinta-feira', 5, FALSE, 2, 'ATIVO', 'Sistema'),
    (2, 4, '2025-12-15', 'Segunda-feira', 3, FALSE, 2, 'ATIVO', 'Sistema'),

    -- Agendamentos de eventos
    (8, 3, '2025-11-15', 'Sexta-feira', 2, TRUE, 2, 'ATIVO', 'Sistema'),
    (9, 3, '2025-11-22', 'Sexta-feira', 3, TRUE, 2, 'ATIVO', 'Sistema'),
    (10, 2, '2025-12-10', 'Terça-feira', 4, TRUE, 2, 'ATIVO', 'Sistema'),
    (8, 3, '2025-11-27', 'Quinta-feira', 1, TRUE, 2, 'ATIVO', 'Sistema'),
    (8, 3, '2025-12-01', 'Segunda-feira', 6, TRUE, 2, 'ATIVO', 'Sistema');

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


INSERT INTO EVENTOS(nome, descricao)
VALUES
('InterFATECS', 'Maratona INTERFATECS de programação'),
('Prova VUNESP', 'Realização da Prova VUNESP'),
('Semana TG', 'Realização da semana de aperfeiçoamento profissional');

-- AgendamentoEventos
INSERT INTO AGENDAMENTO_EVENTOS (agendamento_id, evento_id)
VALUES 
    (11, 1),  -- Evento coordenação - 15/11
    (12, 1),  -- Evento coordenação - 22/11
    (13, 2),  -- Evento admin - 10/12
    (14, 2),  -- Evento coordenação - 27/11
    (15, 1);  -- Evento coordenação - 01/12