INSERT INTO CARGOS(nome)
VALUES ('USER'), ('ADMIN'), ('PROFESSOR');

INSERT INTO USUARIOS (DTYPE, nome, login, senha, email, cargo_id)
VALUES ('Usuario', 'Lucas', '1931', '123123', 'lucasmorais@1022.com', 1);

INSERT INTO USUARIOS (DTYPE, nome, login, senha, email, cargo_id, registro_professor)
VALUES ('PROFESSORES', 'Dimas', '1932', '1234', 'dimas@exemplo.com', 3, 1010);
