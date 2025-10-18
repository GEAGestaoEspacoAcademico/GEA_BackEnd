INSERT INTO CARGOS(nome)
VALUES ('USER'), ('ADMIN'), ('PROFESSOR'), ('COORDENADOR');


INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES ('Lucas', '1931', '123123', 'lucasmorais@1022.com', 1);


INSERT INTO USUARIOS (nome, login, senha, email, cargo_id)
VALUES ('Dimas', '1932', '1234', 'dimas@exemplo.com', 3);

INSERT INTO PROFESSORES (user_id, registro_professor)
VALUES (2, 1010);
