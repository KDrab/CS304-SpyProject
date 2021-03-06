/*
-- users
drop table player;
drop table admin;
-- characters and roles
drop table character;
drop table spy;
drop table businessman;
drop table politician;
-- solo actions
drop table action;
-- player to player actions
drop table message;
drop table kills;
drop table intercepts;
-- multi player groupings
drop table team;
drop table government;
*/

-------------------------
create table player
	(email char(20) not null,
	username char(20) not null,
	password char(15) not null,
	admin	 int null,
	primary key (email));

create table team
	(name char(20) not null,
	primary key (name));

----------------------------

create table character
	(id char(10) not null,
	name char(20) not null,
	lvl int null,
	cash int null,
	email char(20),
	teamName char(20),
	primary key (id),
	foreign key (email) references player ON DELETE CASCADE,
	foreign key (teamName) references team ON DELETE CASCADE,
	check (cash > 0));

create table government
	(name char(20) not null,
	president char(10) not null,
	defcon int null,
	tax int null,
	primary key (name),
	foreign key (president) references character ON DELETE CASCADE);

create table spy
	(id char(10) not null,
	name char(20) not null,
	success int null,
	government char(20),
	primary key (id),
	foreign key (id) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

create table businessman
	(id char(10) not null,
	name char(20) not null,
	return int null,
	government char(20),
	primary key (id),
	foreign key (id) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

create table politician
	(id char(10) not null,
	name char(20) not null,
	popularity int null,
	government char(20),
	primary key (id),
	foreign key (id) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

------------------------------------------

create table action 
 (id char(10) not null,
 email char(20) null,
 time int not null,
 action int null,
 primary key (id, time),
 foreign key (email) references player ON DELETE SET NULL);
-----------------------------------------

create table message
	(id char(10) not null,
	sender char(20) not null,
	receiver char(20) not null,
	message_text char(200) not null,
	primary key (id),
	foreign key (sender) references player ON DELETE SET NULL,
	foreign key (receiver) references player ON DELETE SET NULL);

create table kills
	(spy char(10) not null,
	victim char(10) not null,
	killed int null,
	primary key (spy, victim),
	foreign key (spy) references spy ON DELETE CASCADE,
	foreign key (victim) references character ON DELETE CASCADE);

create table intercepts
	(spy char(10) not null,
	victim char(10) not null,
	intercepted byte null,
	time int null,
	primary key (spy, victim),
	foreign key (spy) references spy ON DELETE CASCADE,
	foreign key (victim) references character ON DELETE CASCADE);
-----------------------------------------------------

-----------------------------------------------------

insert into team
values('best team');

insert into team
values('worst team');

insert into team
values('other team');
-----------------------------------------------------

insert into player
values('player1@gmail.com', 'player1', 'password1', 0);

insert into player
values('player2@gmail.com', 'player2', 'password2', 0);

insert into player
values('player3@gmail.com', 'player3', 'password3', 0);

insert into player
	values ('admin@admin.com', 'admin', 'admin', 1);
-----------------------------------------------------

insert into character
values(11, 'Character 1a', 10, 100, 'player1@gmail.com', 'best team');

insert into character
values(12, 'Character 1b', 8, 150, 'player1@gmail.com', 'worst team');

insert into character
values(21, 'Character 2a', 4, 80, 'player2@gmail.com', 'worst team');

insert into character
values(22, 'Character 2b', 15, 200, 'player2@gmail.com', 'other team');

insert into character
values(23, 'Character 2c', 3, 50, 'player2@gmail.com', 'best team');

insert into character
values(31, 'Character 3a', 1, 20, 'player3@gmail.com', 'other team');
-----------------------------------------------------

insert into government
values('the gov', 22, 2, 10);

insert into spy
values(21, 'Character 2a', 21, 'the gov');

insert into politician
values(22, 'Character 2b', 8, 'the gov');

insert into businessman
values(23, 'Character 2c', 43, 'the gov');
-----------------------------------------------------



insert into action 
values(11, 'player1@gmail.com', 21, 1);

insert into action 
values(11,'player1@gmail.com', 22, 2);

insert into action 
values(11, 'player1@gmail.com', 23, 3);

insert into action 
values(11, 'player1@gmail.com', 24, 4);

insert into action 
values(11, 'player1@gmail.com', 25, 5);

insert into action 
values(11, 'player1@gmail.com', 26, 6);

insert into action 
values(21, 'player2@gmail.com', 27, 2);

insert into action 
values(12, 'player2@gmail.com', 28, 3);

insert into action 
values(12, 'player2@gmail.com', 29, 4);

insert into action 
values(12, 'player2@gmail.com', 30, 5);

insert into action 
values(23, 'player2@gmail.com', 21, 5);

insert into action 
values(31, 'player3@gmail.com', 21, 2);








