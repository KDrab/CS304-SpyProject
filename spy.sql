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

-------------------------
create table player
	(email char(20) not null,
	username char(20) not null,
	password char(15) not null,
	primary key (email));

create table admin
	(email char(20) not null,
	username char(20) not null,
	password char(15) not null,
	primary key (email));

----------------------------

create table character
	(id char(10) not null,
	name char(20) not null,
	level int null,
	cash int null,
	email char(20),
	primary key (id, name, email)
	foreign key (email) references player
	ON DELETE CASCADE);

create table spy
	(id char(10) not null,
	name char(20) not null,
	success int null,
	government char(20),
	primary key (id, name),
	foreign key (id, name) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

create table businessman
	(id char(10) not null,
	name char(20) not null,
	return int null,
	government char(20),
	primary key (id, name),
	foreign key (id, name) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

create table politician
	(id char(10) not null,
	name char(20) not null,
	popularity int null,
	government char(20),
	primary key (id, name),
	foreign key (id, name) references character ON DELETE CASCADE,
	foreign key (government) references government ON DELETE CASCADE);

------------------------------------------

create table action
	(character char(20) not null,
	time int not null,
	action int null,
	primary key (character, time),
	foreign key (character) references character
	ON DELETE CASCADE);
-----------------------------------------

create table message
	(id char(10) not null,
	sender char(20) not null,
	receiver char(20) not null,
	message_text char(200) not null,
	primary key (id),
	foreign key (sender, receiver) references player 
	ON DELETE SET NULL);

create table kills
	(spy char(10) not null,
	victim char(10) not null,
	killed boolean null,
	primary key (spy, victim),
	foreign key (spy) references spy ON DELETE CASCADE,
	foreign key (victim) references character ON DELETE CASCADE);



create table intercepts
	(spy char(10) not null,
	victim char(10) not null,
	intercepted boolean null,
	time int null,
	primary key (spy, victim),
	foreign key (spy) references spy ON DELETE CASCADE,
	foreign key (victim) references character ON DELETE CASCADE);
-----------------------------------------------------

create table team
	(name char(20) not null,
	size int null,
	primary key (name));

create table government
	(name char(20) not null,
	president char(10) not null,
	defcon int null,
	tax int null,
	primary key (name),
	foreign key (president) references character ON DELETE CASCADE);






















































