INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('Lois');
INSERT INTO client(name) VALUES ('Clark');
INSERT INTO client(name) VALUES ('Zod');
INSERT INTO client(name) VALUES ('John');
INSERT INTO client(name) VALUES ('Sarah');
INSERT INTO client(name) VALUES ('Arnold');
INSERT INTO client(name) VALUES ('Patrick');

INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-11-03','2024-11-06', 2 , 4);
INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-11-03','2024-11-06', 1 , 1);
INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-11-04','2024-11-07', 5 , 2);
INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-11-02','2024-11-08', 3 , 3);
INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-12-02','2024-12-08', 2 , 5);
INSERT INTO loan(date_ini, date_final, client_id, game_id) VALUES ('2024-11-17','2024-11-25', 4 , 1);
