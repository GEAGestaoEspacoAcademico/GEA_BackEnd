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
    ('Lucas Silva', 'lucas.silva', 'ls123', 'lucasmorais@1022.com', 1),
    ('Ana Costa', 'ana.costa', 'ac123', 'ana.costa@exemplo.com', 1),
    ('Carlos Santos', 'carlos.santos', 'cs123', 'carlos.santos@exemplo.com', 1);

-- Administrador (usuários com cargo de Administrador)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Auxiliar Docente', 'auxiliar.docente', 'ad123', 'admin@fatec.sp.gov.br', 2);

-- Professores (usuários com cargo de professor)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES
    ('Prof. Sergio Salgado', 'sergio.salgado', 'ss123', 'sergio.salgado@fatec.sp.gov.br', 3),
    ('Prof. Dimas Cardoso', 'dimas.cardoso', 'dc123', 'dimas.cardoso@fatec.sp.gov.br', 3),
    ('Prof. Fabricio Londero', 'fabricio.londero', 'fl123', 'fabricio.londero@fatec.sp.gov.br', 3),
    ('Prof. Francisco Bianchi', 'francisco.bianchi', 'fb123', 'francisco.bianchi@fatec.sp.gov.br', 3),
    ('Prof. Luis dos Santos', 'luis.santos', 'ls123', 'luis.santos@fatec.sp.gov.br', 3);

-- Coordenadores (usuários com cargo de coordenador)
INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES 
    ('Coord. Lucimar de Santi', 'lucimar.santi', 'ls123', 'lucimar.desanti@fatec.sp.gov.br', 4),
    ('Coord. Andre Olimpio', 'andre.olimpio', 'ao123', 'andre.olimpio@fatec.sp.gov.br', 4);



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


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Salas
-- -----------------------------------------------------------------------------
-- Salas normais
INSERT INTO SALAS (nome, capacidade, piso, disponibilidade, is_laboratorio, observacoes)
VALUES 
    ('Sala 101', 40, 1, 1, 0, 'Sala com projetor e ar condicionado'),
    ('Sala 102', 40, 1, 1, 0, 'Sala com lousa digital'),
    ('Sala 201', 50, 2, 1, 0, 'Auditório pequeno'),
    ('Sala 202', 35, 2, 1, 0, 'Sala com projetor');

-- Laboratórios
INSERT INTO SALAS (nome, capacidade, piso, disponibilidade, is_laboratorio, observacoes)
VALUES 
    ('Lab 301 - Informática', 30, 3, 1, 1, 'Laboratório com 30 computadores'),
    ('Lab 302 - Informática', 25, 3, 1, 1, 'Laboratório de redes e infraestrutura'),
    ('Lab 303 - Desenvolvimento', 30, 3, 1, 1, 'Laboratório com computadores high-end');


-- -----------------------------------------------------------------------------
-- Alimenta o BD com Agendamentos
-- -----------------------------------------------------------------------------
-- Agendamentos
INSERT INTO AGENDAMENTOS (user_id, sala_id, data_inicio, data_fim, dia_da_semana, hora_inicio, hora_fim, tipo)
VALUES 
    -- Agendamentos de aulas
    (1, 1, '2025-10-20', '2025-12-15', 'Segunda-feira', '08:00:00', '10:00:00', 'AULA'),
    (1, 1, '2025-10-20', '2025-12-15', 'Quarta-feira', '08:00:00', '10:00:00', 'AULA'),
    (1, 5, '2025-10-20', '2025-12-15', 'Terça-feira', '10:00:00', '12:00:00', 'AULA'),
    (2, 2, '2025-10-20', '2025-12-15', 'Segunda-feira', '10:00:00', '12:00:00', 'AULA'),
    (2, 2, '2025-10-20', '2025-12-15', 'Quinta-feira', '10:00:00', '12:00:00', 'AULA'),
    (1, 5, '2025-10-20', '2025-12-15', 'Sexta-feira', '08:00:00', '10:00:00', 'AULA'),
    (3, 3, '2025-10-20', '2025-12-15', 'Quarta-feira', '14:00:00', '16:00:00', 'AULA'),
    (3, 6, '2025-10-20', '2025-12-15', 'Terça-feira', '14:00:00', '16:00:00', 'AULA'),
    (1, 7, '2025-10-20', '2025-12-15', 'Quinta-feira', '14:00:00', '16:00:00', 'AULA'),
    (2, 4, '2025-10-20', '2025-12-15', 'Segunda-feira', '14:00:00', '16:00:00', 'AULA'),
    
    -- Agendamentos de eventos
    (8, 3, '2025-11-15', '2025-11-15', 'Sexta-feira', '19:00:00', '22:00:00', 'EVENTO'),
    (9, 3, '2025-11-22', '2025-11-22', 'Sexta-feira', '19:00:00', '22:00:00','EVENTO'),
    (10, 2, '2025-12-10', '2025-12-10', 'Terça-feira', '18:00:00', '21:00:00','EVENTO');

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

