# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comentario (
  id                        bigint not null,
  ativo                     boolean,
  descricao                 varchar(255),
  operacao_id               bigint,
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_comentario primary key (id))
;

create table desenvolvedor (
  id                        bigint not null,
  ativo                     boolean,
  nome                      varchar(255),
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_desenvolvedor primary key (id))
;

create table operacao (
  id                        bigint not null,
  ativo                     boolean,
  codigo                    varchar(255),
  descricao                 varchar(255),
  produto_id                bigint,
  desenvolvedor_id          bigint,
  status_id                 bigint,
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_operacao primary key (id))
;

create table produto (
  id                        bigint not null,
  ativo                     boolean,
  descricao                 varchar(255),
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_produto primary key (id))
;

create table status_op (
  id                        bigint not null,
  ativo                     boolean,
  descricao                 varchar(255),
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_status_op primary key (id))
;

create table usuario (
  id                        bigint not null,
  ativo                     boolean,
  nome                      varchar(255),
  data_criacao              timestamp not null,
  data_atualizacao          timestamp not null,
  constraint pk_usuario primary key (id))
;

create sequence comentario_seq;

create sequence desenvolvedor_seq;

create sequence operacao_seq;

create sequence produto_seq;

create sequence status_op_seq;

create sequence usuario_seq;

alter table comentario add constraint fk_comentario_operacao_1 foreign key (operacao_id) references operacao (id);
create index ix_comentario_operacao_1 on comentario (operacao_id);
alter table operacao add constraint fk_operacao_produto_2 foreign key (produto_id) references produto (id);
create index ix_operacao_produto_2 on operacao (produto_id);
alter table operacao add constraint fk_operacao_desenvolvedor_3 foreign key (desenvolvedor_id) references desenvolvedor (id);
create index ix_operacao_desenvolvedor_3 on operacao (desenvolvedor_id);
alter table operacao add constraint fk_operacao_status_4 foreign key (status_id) references status_op (id);
create index ix_operacao_status_4 on operacao (status_id);



# --- !Downs

drop table if exists comentario cascade;

drop table if exists desenvolvedor cascade;

drop table if exists operacao cascade;

drop table if exists produto cascade;

drop table if exists status_op cascade;

drop table if exists usuario cascade;

drop sequence if exists comentario_seq;

drop sequence if exists desenvolvedor_seq;

drop sequence if exists operacao_seq;

drop sequence if exists produto_seq;

drop sequence if exists status_op_seq;

drop sequence if exists usuario_seq;

