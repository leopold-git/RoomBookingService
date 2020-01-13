create database ReserveOxy;

use ReserveOxy;

create table Student(
	stu_id int not null, 
	stu_name varchar(64),
	email varchar(64),
	major varchar(32),
	primary key (stu_id));

create table Dept(
	dept_name varchar(64) not null,
	d_budget double(10,2),
	primary key (dept_name));
    
create table Faculty (
	fac_id int not null,
	fac_name varchar(64),
	dept_name varchar(64),
    head boolean,
	primary key (fac_id));

create table Club(
	club_name varchar(64) not null,
	club_category varchar(32),
	c_budget double(10,2),
	presID int,
    advisor_id int,
	dept_name varchar(64),
	primary key (club_name, advisor_id),
    foreign key (advisor_id) references Faculty (fac_id),
	foreign key (dept_name) references Dept (dept_name));

create table Membership(
	stu_id int not null,
	club_name varchar(64) not null,
	primary key (stu_id, club_name),
	foreign key (stu_id) references Student(stu_id),
	foreign key (club_name) references Club(club_name)); 

create table Building(
	time_stamp datetime not null,
    building_name varchar(64) not null,
	room_no int not null,
	max_cap int,
	stu_id int,
	fac_id int,
	primary key (time_stamp, building_name, room_no),
	foreign key (fac_id) references Faculty (fac_id),
	foreign key (stu_id) references Student (stu_id));

create table Course(
    crn char(4) not null,
	c_name varchar(64),
	c_start_time varchar(10),
	c_end_time varchar(10),
	mon boolean,
	tue boolean,
	wed boolean,
	thu boolean,
	fri boolean,
	time_stamp datetime,
    building_name varchar(64),
	room_no int,
	fac_id int,
	primary key (crn),
	foreign key (time_stamp, building_name, room_no) references Building(time_stamp, building_name, room_no),
	foreign key (fac_id) references Faculty (fac_id));

create table Event (
    event_name varchar(128) not null,
	supplier varchar(64),
	e_start_time varchar(10),
	e_end_time varchar(10),
	date date,
	club_name varchar(64),
	time_stamp datetime,
    building_name varchar(64) not null,
	room_no int not null,
	presID int not null,
	fac_id int not null,
	primary key (event_name, building_name, room_no, presID, fac_id),
	foreign key (club_name) references Club (club_name),
	foreign key (time_stamp, building_name, room_no) references Building (time_stamp, building_name, room_no),
    foreign key (presID) references Student(stu_id),
	foreign key (fac_id) references Faculty(fac_id));

create table c_in (
	crn char(4) not null,
	time_stamp datetime not null,
    building_name varchar(64) not null,
	room_no int not null,
	primary key (crn, building_name, room_no),
	foreign key (crn) references Course(crn),
	foreign key (time_stamp, building_name, room_no) references Building (time_stamp, building_name, room_no));
