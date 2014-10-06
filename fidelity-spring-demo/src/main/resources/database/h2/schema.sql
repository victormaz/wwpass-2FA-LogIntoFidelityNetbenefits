create table demo_users (
  id bigint identity,
  username varchar(256) not null unique,
  password varchar(256) not null,
  nickname varchar(256) not null,
  role_id bigint not null
);

create table authorities (
  id bigint identity,
  rolename varchar(256) not null
);
