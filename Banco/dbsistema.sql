/**
*Sistem para gestão de OS
*@author Haruki Urata (THE GREATEST)
*/

create database dbsistema;
use dbsistema;
show tables;

create table usuarios(
	id int primary key auto_increment,
	nome varchar(30) not null,
	login varchar(15) not null,
	senha varchar(16) not null
);

drop table usuarios;


describe usuarios;
select * from usuarios;
-- uso do md5() para criptografar uma senha
insert into usuarios (nome,login,senha) values ('Haruki Urata','haruki',md5('123@senac'));

-- CRUD READ
select nome from usuarios;
select nome,login from usuarios;
select * from usuarios where nome like 'al%';
select * from usuarios where nome = 'Haruki Urata';
select * from usuarios where id = 1;
select * from usuarios where login = 1;
