alter table addresses drop column state;
alter table addresses add column country varchar(45) not null;