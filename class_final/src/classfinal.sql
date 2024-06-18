create database injection;
use injection;
create table vaccine_storage(
vac_code char(4) primary key,
vac_name varchar(10) not null,
quantity int check (quantity > 0) not null
);
create table participant(
par_code char(5) primary key check (par_code like 'P[0-9][0-9][0-9]'),
par_name varchar(30) not null,
age int not null,
gender varchar(6) check (gender like 'Male' or gender like 'Female') not null,
phone_number char(10) check (phone_number like '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
vac_code char(4) foreign key references vaccine_storage(vac_code)on Delete Cascade not null,
register_date DATE check (register_date <= getdate()),
inject_date DATE
);
create table medical_staff(
st_code char(5) primary key check (st_code like 'S[0-9][0-9][0-9]' or st_code like 'D[0-9][0-9][0-9]'),
st_name varchar(30) not null,
res char(6) check (res like 'Staff' or res like 'Doctor') not null
);
--create table doctor(
--doc_code char(5) primary key check (doc_code like 'D[0-9][0-9][0-9]'),
--doc_name varchar(30) not null,
--res char(6) check (res like 'Doctor') not null
--);
create table statistic(
vac_code char(4) foreign key references vaccine_storage(vac_code) not null,
par_code char(5) foreign key references participant(par_code) on Delete Cascade not null,
is_injected varchar(3) check (is_injected like 'Yes' or is_injected like 'No'),
);
create table admin_acc(
acc_name char(13),
pass varchar(20),
attempts int,
reset_acc char(8)
);
create table numb_code(
numb int,
st_numb int,
doc_numb int
);
insert into vaccine_storage(vac_code, vac_name, quantity) values ('PF01', 'Pfizer', 500), ('VA07', 'VAT', 300), ('IV01', 'Imojev', 200)
insert into participant(par_code, par_name, age, gender, phone_number, vac_code, register_date, inject_date) 
values ('P001', 'Le Xuan Dao', 19, 'Male', '0914321567', 'VA07', CONVERT(DATE,'15/05/2024',103), CONVERT(DATE,'20/05/2024',103)),
('P002', 'Nguyen Thuy Duong', 13, 'Female', '0924341542', 'IV01', CONVERT(DATE,'01/06/2024',103),CONVERT(DATE,'12/06/2024',103) ),
('P003', 'Le Minh Chau', 24, 'Male', '0945218962','PF01', CONVERT(DATE,'04/06/2024',103), CONVERT(DATE,'15/06/2024',103)),
('P004', 'Nguyen Tuan Tu', 40, 'Male', '0993321098','VA07', CONVERT(DATE,'10/05/2024',103), CONVERT(DATE,'15/05/2024',103)),
('P005', 'Tran Thao Linh', 25, 'Female', '0956420009','PF01', CONVERT(DATE,'08/06/2024',103), CONVERT(DATE,'10/06/2024',103)),
('P006', 'Le Dinh Phong', 24, 'Male', '0975124658', 'PF01', CONVERT(DATE,'04/04/2024',103), CONVERT(DATE,'05/04/2024',103)),
('P007', 'Nguyen Hoang Minh', 4, 'Male', '0979693147', 'IV01', CONVERT(DATE,'10/05/2024',103), CONVERT(DATE,'13/05/2024',103)),
('P008', 'Hoang Minh Cuong', 33, 'Male', '0983789681','VA07', CONVERT(DATE,'10/04/2024',103), CONVERT(DATE,'09/04/2024',103)),
('P009', 'Le Thi Ngoc Bich', 16, 'Female','0915986742', 'PF01', CONVERT(DATE,'08/06/2024',103), CONVERT(DATE,'12/06/2024',103));
insert into medical_staff(st_code, st_name, res) values ('S001', 'Nguyen Huyen Trang', 'Staff'),
('S002', 'Le Quang Trung', 'Staff'),
('S003', 'Mai Van Trang', 'Staff');
insert into medical_staff(st_code, st_name, res) values ('D001', 'Le Tran Dat', 'Doctor'), 
('D002', 'Nguyen Hong Nhien', 'Doctor'),
('D003', 'Nguyen Thi Anh', 'Doctor'), 
('D004', 'Tran Van Hau', 'Doctor'),
('D005', 'Le Dinh Phung', 'Doctor'), 
('D006', 'Nguyen Thi Hao', 'Doctor');
insert into statistic(vac_code, par_code, is_injected) values ('VA07','P001','Yes'),
('IV01', 'P002', 'Yes'),
('PF01','P003', 'No'),
('VA07','P004', 'Yes'),
('PF01','P005', 'Yes'),
('PF01','P006', 'Yes'),
('IV01','P007', 'Yes'),
('VA07','P008', 'No'),
('VA07','P009', 'No');
insert into admin_acc(acc_name,pass,attempts, reset_acc) values ('injectionclub', 'doctor123', 1, 'docreset');
insert into numb_code(numb, st_numb, doc_numb) values (0,0,0);
UPDATE numb_code SET numb = (SELECT COUNT(par_code) FROM participant), st_numb = (Select Count(st_code) from medical_staff where st_code like 'S%'), doc_numb= (Select Count(st_code) from medical_staff where st_code like 'D%');
select * from vaccine_storage;
select * from participant
select * from medical_staff 
select * from statistic;
select * from admin_acc;
select * from numb_code;
drop table admin_acc
drop table medical_staff
drop table participant
---drop table doctor
drop table statistic
drop table numb_code
drop table vaccine_storage
---insert into participant(par_code, par_name, age, gender, phone_number, vac_code, register_date, inject_date) 
---values ('P010', 'Le Xuan Dao', 19, 'Male', '0914321567', 'VA07', '2024-05-15', '2024-06-18')
---insert into statistic(vac_code, par_code, is_injected) values ('VA07', 'P010', 'No')
---delete from statistic where par_code = 'P009' and vac_code = 'PF01'
---delete from participant where par_code='P010';
---delete from vaccine_storage where vac_code = 'VA07'
---delete from medical_staff where st_code = 'S004' or st_code = 'D007'
---UPDATE numb_code SET numb = numb+1;
---update participant set par_name = 'Le Minh Trau' where par_code = 'P003'
---select * from participant where inject_date = '2024-06-12'
---select count(par_code) from participant
---Select p.par_code, p.par_name, p.inject_date, p.phone_number, v.vac_code, v.vac_name, s.is_injected from statistic s inner join participant p on p.par_code = s.par_code inner join vaccine_storage v on v.vac_code = s.vac_code
---update admin_acc set attempts = 1
---Select inject_date, par_code from participant
---select par_code from statistic where is_injected = 'No'
---Select phone_number from participant where phone_number = '0914321567' and par_code = 'P001'
---Delete from vaccine_storage, statistic where vac_code = VA07
---Delete p, s from participant p join statistic s on p.par_code = s.par_code where p.inject_date = '2024-05-20' and s.is_injected = 'No'
--alter table admin_acc
--add attemps int;
--add reset_acc char(10);