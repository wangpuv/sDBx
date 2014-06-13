drop table if exists sdbx_main_acct;

create table sdbx_main_acct (
  main_acct_id bigint,
  login_name   varchar(255),
  user_name    varchar(255),
  valid        varchar(255),
  lock_status  varchar(255),
  acct_type    varchar(255),
  effect_time  timestamp
);

delete from sdbx_main_acct;
commit;

insert into sdbx_main_acct (main_acct_id, login_name, user_name, valid, lock_status, acct_type, effect_time)
  values (12345, 'test1', 'test1', '1', '0', '1', '2013-04-14 10:07:08');
insert into sdbx_main_acct (main_acct_id, login_name, user_name, valid, lock_status, acct_type, effect_time)
  values (12346, 'test2', 'test2', '1', '0', '1', '2013-04-15 11:07:08');
insert into sdbx_main_acct (main_acct_id, login_name, user_name, valid, lock_status, acct_type, effect_time)
  values (12347, 'test3', 'test3', '1', '0', '1', '2013-04-16 12:07:08');
commit;