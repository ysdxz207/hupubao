
set time zone 'Asia/Shanghai';

DROP TABLE IF EXISTS afu;
create table afu
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  name        VARCHAR(128)            not null
    unique,
  type        VARCHAR(64) default NULL,
  create_time TIMESTAMP default now() not null,
  content     TEXT        default NULL
);

DROP TABLE IF EXISTS afu_type;
create table afu_type
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  name        VARCHAR(128)            not null
    unique,
  tag         VARCHAR(128) default NULL,
  private_key TEXT                    not null,
  public_key  TEXT                    not null,
  md5_key  VARCHAR(64)                not null,
  create_time TIMESTAMP default now() not null,
  status      SMALLINT default 1      not null
);

DROP TABLE IF EXISTS article;
create table article
(
  id               VARCHAR(32)                not null
    primary key
    unique,
  creator          VARCHAR(128)               not null,
  title            VARCHAR(256)               not null,
  context          TEXT,
  category_id      VARCHAR(32),
  create_time      TIMESTAMP default now()    not null,
  last_update_time TIMESTAMP default now()    not null,
  type             VARCHAR(10) default 'yiyi' not null,
  status           SMALLINT default '1',
  intro            TEXT
);

DROP TABLE IF EXISTS article_tag;
create table article_tag
(
  id         VARCHAR(32) not null
    primary key
    unique,
  article_id VARCHAR(32) not null,
  tag_id     VARCHAR(32) not null
);

DROP TABLE IF EXISTS category;
create table category
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  name        VARCHAR(128)            not null,
  create_time TIMESTAMP default now() not null
);

DROP TABLE IF EXISTS image;
create table image
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  name        TEXT                    not null,
  base64      TEXT                    not null,
  url         TEXT                    null,
  create_time TIMESTAMP default now() not null
);

DROP TABLE IF EXISTS menu;
create table menu
(
  id          VARCHAR(32)                not null
    primary key
    unique,
  menu_name   VARCHAR(128)               not null,
  icon        VARCHAR(64)   default NULL,
  sort        VARCHAR(64)   default NULL,
  href        VARCHAR(2000) default NULL,
  status      SMALLINT      default '1',
  remark      VARCHAR(256)  default NULL,
  type        VARCHAR(32) default 'blog' not null,
  code        VARCHAR(128)  default NULL,
  pid         VARCHAR(32) default '0'    not null,
  expand      SMALLINT      default '0',
  create_time TIMESTAMP default now()    not null
);

DROP TABLE IF EXISTS permission;
create table permission
(
  id              VARCHAR(32)             not null
    primary key
    unique,
  menu_id         VARCHAR(32)             not null,
  permission_name VARCHAR(128)            not null,
  permission      VARCHAR(128) default NULL,
  create_time     TIMESTAMP default now() not null
);

DROP TABLE IF EXISTS role;
create table role
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  role_name   VARCHAR(32)             not null,
  code        VARCHAR(64)             not null,
  create_time TIMESTAMP default now() not null
);

DROP TABLE IF EXISTS role_permission;
create table role_permission
(
  id            VARCHAR(32) not null
    primary key
    unique,
  role_id       VARCHAR(32) not null,
  permission_id VARCHAR(32) not null
);

DROP TABLE IF EXISTS tag;
create table tag
(
  id          VARCHAR(32)             not null
    primary key
    unique,
  name        VARCHAR(128)            not null,
  create_time TIMESTAMP default now() not null
);

DROP TABLE IF EXISTS "user";
create table "user"
(
  id              VARCHAR(32)             not null
    primary key
    unique,
  username        VARCHAR(64)             not null,
  nickname        VARCHAR(64)             not null,
  password        VARCHAR(64)             not null,
  create_time     TIMESTAMP default now() not null,
  last_login_time TIMESTAMP default now() not null,
  status          SMALLINT default '1'
);

DROP TABLE IF EXISTS user_role;
create table user_role
(
  id      VARCHAR(32) not null
    primary key
    unique,
  user_id VARCHAR(32) not null,
  role_id VARCHAR(32) not null
);

DROP TABLE IF EXISTS user_security;
create table user_security
(
  id          VARCHAR(32)
    primary key
    unique,
  user_id     VARCHAR(32),
  des_key     VARCHAR(16),
  create_time TIMESTAMP default now() not null
);
