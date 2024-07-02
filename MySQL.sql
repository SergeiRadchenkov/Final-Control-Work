-- Создаём базу данных
create database HumanFriends;
use HumanFriends; 

-- Создаём таблицы типов животных
create table if not exists Pets (
	ID int auto_increment primary key,
    type_an varchar(50) not null unique
);

create table if not exists PackAnimals (
	ID int auto_increment primary key,
    type_an varchar(50) not null unique
);

-- Создаём таблицы видов животных
create table if not exists Dogs (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references Pets(ID)
);

create table if not exists Cats (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references Pets(ID)
);

create table if not exists Hamsters (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references Pets(ID)
);

create table if not exists Horses (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references PackAnimals(ID)
);

create table if not exists Camels (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references PackAnimals(ID)
);

create table if not exists Donkeys (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references PackAnimals(ID)
);

-- Заполняем таблицы
insert into Pets (type_an) values
	('Собака'),
    ('Кот'),
    ('Хомяк');
    
insert into Packanimals (type_an) values
	('Лошадь'),
    ('Верблюд'),
    ('Осёл');

insert into Dogs (type_id, name_an, birthdate, command) values
	(1, 'Барсик', '2010-03-10', 'Охрана дома'),
    (1, 'Шарик', '2020-05-15', 'Работа в полиции'),
    (1, 'Боксёр', '2023-03-10', 'Поиск вещей');

insert into Cats (type_id, name_an, birthdate, command) values
	(2, 'Мурзик', '2018-07-01', 'Ловля мышей'),
    (2, 'Васька', '2020-05-15', 'Спит на кровати'),
    (2, 'Кузя', '2023-03-10', 'Царапается');

insert into Hamsters (type_id, name_an, birthdate, command) values
	(3, 'Буся', '2022-01-10', 'Крутит колесо'),
    (3, 'Пуся', '2023-02-05', 'Бегает по клетке'),
    (3, 'Мякиш', '2024-02-07', 'Лежит на спинке');

insert into Horses (type_id, name_an, birthdate, command) values
	(1, 'Грация', '2017-09-10', 'Верховая езда'),
    (1, 'Спирит', '2018-11-15', 'Скачки'),
    (1, 'Цезарь', '2022-06-18', 'Пашет огород');

insert into Camels (type_id, name_an, birthdate, command) values
	(2, 'Шарип', '2016-05-20', 'Перевозка грузов'),
    (2, 'Камила', '2020-08-25', 'Туризм'),
    (2, 'Хеопс', '2022-07-28', 'Поиск воды');
    
insert into Donkeys (type_id, name_an, birthdate, command) values
	(3, 'Иван', '2020-04-12', 'Сельскохозяйственные работы'),
    (3, 'Мария', '2021-06-18', 'Тяжёлый труд'),
    (3, 'Иа', '2022-02-24', 'Поездки');

-- Удаляем из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку
delete from Camels where type_id = 2;

select * from Camels;

-- Объедининяем таблицы лошади и ослы в одну таблицу Equines
create table if not exists Equines (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    command varchar(100),
    foreign key (type_id) references PackAnimals(ID)
);

insert into Equines (type_id, name_an, birthdate, command)
select type_id, name_an, birthdate, command from Horses
union all
select type_id, name_an, birthdate, command from Donkeys;

select * from Equines;

/*
Создаём новую таблицу “молодые животные” в которую попадут все
животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
до месяца подсчитать возраст животных в новой таблице
*/
create table if not exists YoungAnimals (
	ID int auto_increment primary key,
    type_id int not null,
    name_an varchar(100) not null,
    birthdate date not null,
    age_month int
);

insert into YoungAnimals (type_id, name_an, birthdate, age_month)
select type_id, name_an, birthdate,
	timestampdiff(month, birthdate, curdate()) as age_month
from Equines
where birthdate between date_sub(curdate(), interval 3 year) and date_sub(curdate(), interval 1 year);

insert into YoungAnimals (type_id, name_an, birthdate, age_month)
select type_id, name_an, birthdate,
	timestampdiff(month, birthdate, curdate()) as age_month
from Cats
where birthdate between date_sub(curdate(), interval 3 year) and date_sub(curdate(), interval 1 year);

insert into YoungAnimals (type_id, name_an, birthdate, age_month)
select type_id, name_an, birthdate,
	timestampdiff(month, birthdate, curdate()) as age_month
from Dogs
where birthdate between date_sub(curdate(), interval 3 year) and date_sub(curdate(), interval 1 year);

insert into YoungAnimals (type_id, name_an, birthdate, age_month)
select type_id, name_an, birthdate,
	timestampdiff(month, birthdate, curdate()) as age_month
from Hamsters
where birthdate between date_sub(curdate(), interval 3 year) and date_sub(curdate(), interval 1 year);

select * from YoungAnimals;

-- Объедининяем все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам
create table if not exists AllAnimals (
	ID int auto_increment primary key,
    original_table varchar(50) not null,
    type_an varchar(50) not null,
    name_an varchar(100),
    birthdate date,
    command varchar(100)
);

insert into AllAnimals (original_table, type_an, name_an, birthdate, command)
select 'Dogs', (select type_an from Pets where ID = Dogs.type_id), name_an, birthdate, command
from Dogs
union all
select 'Cats', (select type_an from Pets where ID = Cats.type_id), name_an, birthdate, command
from Cats
union all
select 'Hamsters', (select type_an from Pets where ID = Hamsters.type_id), name_an, birthdate, command
from Hamsters
union all
select 'Equines', (select type_an from PackAnimals where ID = Equines.type_id), name_an, birthdate, command
from Equines;

select * from AllAnimals;