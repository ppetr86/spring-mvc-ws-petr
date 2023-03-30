alter table currencies add constraint unique_name UNIQUE (name);
alter table currencies add constraint unique_symbol UNIQUE (symbol);
alter table currencies add constraint unique_code UNIQUE (code);