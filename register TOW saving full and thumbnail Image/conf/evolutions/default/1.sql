# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  created_at                varchar(255) not null,
  updated_at                varchar(255) not null,
  constraint pk_user primary key (id))
;

create table user_image_ids (
  id                        bigint auto_increment not null,
  image_id                  varchar(255) not null,
  full_image_path           varchar(255) not null,
  thumbnail_image_path      varchar(255) not null,
  created_at                varchar(255) not null,
  constraint pk_user_image_ids primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user;

drop table user_image_ids;

SET FOREIGN_KEY_CHECKS=1;

