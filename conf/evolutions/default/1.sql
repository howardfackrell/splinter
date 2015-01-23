# --- !Ups
create table things (
  id serial PRIMARY KEY ,
  something text
);

# --- !Downs
drop table if exists things;

