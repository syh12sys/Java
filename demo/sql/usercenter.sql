show databases

insert into usercenter.user_info(username,  password) values ('孙迎世', '123456')

update usercenter.user_info set password='23456' where  usercenter.user_info.username='孙迎世'

update usercenter.user_info set username='孙迎世' where username='孙迎世123'

use usercenter
SELECT * FROM user_info;

select * from user_info_detail

select * from user_info where token='afec5cba3d65ab37ce74851ab0b681ad'

update user_info set token= where id=#{id}

delete from user_info where id = 4

#增加列
alter table user_info add phone_number varchar(128)

alter table user_info add address varchar(128)

alter table user_info add login_datetime DATETIME

alter table user_info add test_optimistic_lock_count integer default 0

alter table usercenter.user_info modify token varchar(128)

alter table user_info drop column login_datatime

alter table user_info drop column address

update user_info set token='afec5cba3d65ab37ce74851ab0b681ad', login_datetime=now() where id=1

update user_info set test_optimistic_lock_count = test_optimistic_lock_count + 1, update_at=now() where id = 1 and update_at = STR_TO_DATE('2020-07-20 11:57:40')
