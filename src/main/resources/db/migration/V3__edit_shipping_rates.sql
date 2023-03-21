alter table shipping_rates drop column country_id;
alter table shipping_rates add column country varchar(45) not null unique;
alter table shipping_rates add unique index idx_shipping_rates_country (country);