# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table registration_ids (
  id                        bigint auto_increment not null,
  device_token              varchar(255),
  email                     varchar(255),
  created_at                varchar(255),
  updated_at                varchar(255),
  user_id                   bigint,
  constraint pk_registration_ids primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  created_at                varchar(255) not null,
  updated_at                varchar(255) not null,
  auth_token                varchar(255),
  token_created_at          bigint,
  constraint pk_user primary key (id))
;

create table user_image_ids (
  id                        bigint auto_increment not null,
  image_id                  varchar(255),
  full_image_path           varchar(255),
  thumbnail_image_path      varchar(255),
  created_at                varchar(255),
  user_id                   bigint,
  constraint pk_user_image_ids primary key (id))
;

alter table registration_ids add constraint fk_registration_ids_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_registration_ids_user_1 on registration_ids (user_id);
alter table user_image_ids add constraint fk_user_image_ids_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_image_ids_user_2 on user_image_ids (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table registration_ids;

drop table user;

drop table user_image_ids;

SET FOREIGN_KEY_CHECKS=1;

