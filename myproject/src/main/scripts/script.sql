create table senior_project.user (
ID int,
userName varchar(40),
password varchar(40),
primary key (ID)
);
insert into senior_project.user values (1, 'bbrookes','123');
insert into senior_project.user values (2,'mhoult','123');

create table senior_project.account (
ID int auto_increment,
userId int,
balance long,
primary key (ID)
);
insert into senior_project.account (userId, balance) values ( 1,100);
insert into senior_project.account  (userId, balance) values ( 2,200);
insert into senior_project.account  (userId, balance) values ( 1,300);
insert into senior_project.account  (userId, balance) values (2,400);

