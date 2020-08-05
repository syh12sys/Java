drop database if exists cyhTest
create database cyhTest
use cyhTest
drop table if exists employee
create table if not exists employee (
id integer not null,
money integer,
version integer,
primary key(id)
)

insert into employee values (1, 0, 1)

select * from employee
#关闭自动提交
set autocommit = 0

#数据库行锁
select * from employee where id = 1 for update
update employee set money = money + 1 where id = 1
commit

set autocommit = 1
