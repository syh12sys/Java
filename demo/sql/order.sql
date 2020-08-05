SELECT * FROM usercenter.order;
insert into usercenter.order (date, commodity_id, order_id, user_id) values(now(), '123456', '2012007280001', 1)

alter table usercenter.order modify order_id varchar(128)

alter table usercenter.order modify user_id int

 select now()
 
 select usercenter.order.* from usercenter.order join usercenter.user_info on usercenter.order.user_id = usercenter.user_info.id where usercenter.user_info.token = '7a70d08adf22148c97aca255286f2c1b'
 
 
 select order_info.* from order_info join user_info on order_info.user_id = user_info.id where user_info.token='afec'