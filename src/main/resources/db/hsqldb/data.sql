-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner4','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner5','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner6','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner7','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner8','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner9','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner10','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
INSERT INTO authorities VALUES ('owner2','owner');
INSERT INTO authorities VALUES ('owner3','owner');
INSERT INTO authorities VALUES ('owner4','owner');
INSERT INTO authorities VALUES ('owner5','owner');
INSERT INTO authorities VALUES ('owner6','owner');
INSERT INTO authorities VALUES ('owner7','owner');
INSERT INTO authorities VALUES ('owner8','owner');
INSERT INTO authorities VALUES ('owner9','owner');
INSERT INTO authorities VALUES ('owner10','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet2','v3t',TRUE);
INSERT INTO authorities VALUES ('vet2','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet3','v3t',TRUE);
INSERT INTO authorities VALUES ('vet3','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet4','v3t',TRUE);
INSERT INTO authorities VALUES ('vet4','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet5','v3t',TRUE);
INSERT INTO authorities VALUES ('vet5','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet6','v3t',TRUE);
INSERT INTO authorities VALUES ('vet6','veterinarian');

-- Animal shelters' Users
INSERT INTO users(username,password,enabled) VALUES ('shelter1','shelter1',TRUE);
INSERT INTO authorities VALUES ('shelter1','animalshelter');
INSERT INTO users(username,password,enabled) VALUES ('shelter2','shelter2',TRUE);
INSERT INTO authorities VALUES ('shelter2','animalshelter');

INSERT INTO vets VALUES (1, 'James', 'Carter', 'vet1');
INSERT INTO vets VALUES (2, 'Helen', 'Leary', 'vet2');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas', 'vet3');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega', 'vet4');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens', 'vet5');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins', 'vet6');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner2');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner3');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner4');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner5');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner6');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner7');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner8');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner9');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner10');
INSERT INTO owners VALUES (11, 'Pichú Animales', 'Shelter', '41410 La Celada', 'Seville', '610839583', 'shelter1');
INSERT INTO owners VALUES (12, 'Arca Sevilla', 'Shelter', '41500 Alcalá de Guadaíra', 'Seville', '6085555487', 'shelter2');

INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 'female', 1, 1);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 'female', 6, 2);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 'female', 2, 3);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 'female', 2, 3);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 'female', 3, 4);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 'female', 4, 5);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 'female', 1, 6);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 'female', 1, 6);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 'male', 5, 7);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 'male', 2, 8);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 'male', 5, 9);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 'male', 2, 10);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 'male', 1, 10);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (14, 'Desto', '2016-06-18', 'male', 3, 11);
INSERT INTO pets(id,name,birth_date,genre,type_id,owner_id) VALUES (15, 'Puesto', '2015-11-25', 'male', 6, 12);


INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO animalshelters(id,name,cif,place,owner_id) VALUES (1,'Pichú Animales', '12345678A', '41410 La Celada',11);
INSERT INTO animalshelters(id,name,cif,place,owner_id) VALUES (2,'Arca Sevilla', '87654321B', '41500 Alcalá de Guadaíra',12);

INSERT INTO appointments(id,cause,date,urgent,owner_id,pet_id,vet_id) VALUES (1, 'El animal esta malo','2020-04-01','true', 6, 7, 2);
INSERT INTO appointments(id,cause,date,urgent,owner_id,pet_id,vet_id) VALUES (2, 'Operacion','2020-05-01','true', 1, 1, 1);
INSERT INTO appointments(id,cause,date,urgent,owner_id,pet_id,vet_id) VALUES (3, 'Revision','2020-12-18','false', 5, 11, 1);
INSERT INTO appointments(id,cause,date,urgent,owner_id,pet_id,vet_id) VALUES (4, 'Cita ya pasada','2019-12-18','false', 5, 11, 1);

INSERT INTO notification(id,title,date,message,target) VALUES (1, '¡Bienvenidos propietarios de animales!', '2013-01-04 12:32', 'Quiero daros la bienvenida a todos los propietarios de animales','owner');
INSERT INTO notification(id,title,date,message,target, url) VALUES (2, '¡Mira esta página de amazonas!', '2020-02-15 16:32', 'Puede que si te interesa el mundo de la monta a la amazona quieras visitar esta página web','owner','https://www.asociacionandaluzademontaalaamazona.com/');
INSERT INTO notification(id,title,date,message,target) VALUES (3, '¡Bienvenidos veterinarios!', '2013-01-04 12:32', 'Quiero daros la bienvenida a todos los veterinarios','veterinarian');
INSERT INTO notification(id,title,date,message,target, url) VALUES (4, '¡Mira esta página de veterinarios!', '2020-02-10 16:32', 'Creemos que os puede interesar leer la página de wikipedia de veterinaria','veterinarian','https://es.wikipedia.org/wiki/Medicina_veterinaria');
INSERT INTO notification(id,title,date,message,target) VALUES (5, '¡Bienvenidas protectoras!', '2013-01-04 12:32', 'Quiero daros la bienvenida a todas las protectoras','animal_shelter');
INSERT INTO notification(id,title,date,message,target, url) VALUES (6, '¡Mira esta página de información sobre protectoras!', '2020-02-20 11:32', 'Creemos que os puede interesar leer esta página con información sobre protectora de animales','animal_shelter','http://www.upv.es/proanimales/protectoras.htm');

INSERT INTO questionnaires(id,name,umbral,vivienda,ingresos,horas_Libres,convivencia,puntuacion,pet_id,owner_id) VALUES (1, 'Quest-George Franklin',4,'Casa','Altos', 'Entre 3 y 6 horas','Sí',5,14,1);
INSERT INTO questionnaires(id,name,umbral,vivienda,ingresos,horas_Libres,convivencia,puntuacion,pet_id,owner_id) VALUES (2, 'Quest-Betty Davis',4,'Apartamento','Bajos', 'Menos de 3 horas','No',0,15,2);

INSERT INTO records(id,owner_id,animalshelter_id) VALUES (1, 1, 11);

INSERT INTO product(id,name,price,stock,description,image) VALUES (1, 'Comida para perros',12.2, true, 'La mejor comida para alimentar a nuestros compañeros caninos', 'https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg');
INSERT INTO product(id,name,price,stock,description,image) VALUES (2, 'Comida para gatos',15.4, false, 'La mejor comida para alimentar a nuestros compañeros felinos', 'https://t1.uc.ltmcdn.com/images/1/9/0/img_como_hacer_comida_casera_saludable_para_mi_gato_23091_orig.jpg');


