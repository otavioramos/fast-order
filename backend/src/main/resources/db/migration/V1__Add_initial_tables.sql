-- Schema
create schema if not exists fast_order;

-- Sequences
create sequence if not exists fast_order.order_item_sequence start 1;
create sequence if not exists fast_order.order_product_sequence start 1;
create sequence if not exists fast_order.order_status_sequence start 1;
create sequence if not exists fast_order.order_payment_sequence start 1;
create sequence if not exists fast_order.order_sequence start 1;

-- Tables
create table if not exists fast_order.order (
   id integer primary key default nextval('fast_order.order_sequence'),
   creation_time timestamp not null DEFAULT current_timestamp
);

create table if not exists fast_order.order_payment (
   id integer primary key default nextval('fast_order.order_payment_sequence'),
   payment_method varchar(50) not null,
   total numeric not null,
   installments integer not null
);

create table if not exists fast_order.order_status (
   id integer primary key default nextval('fast_order.order_status_sequence'),
   current_status varchar(80) not null,
   total money not null,
   installments integer not null,
   deadline  timestamp not null
);

create table if not exists fast_order.order_product (
   id integer primary key default nextval('fast_order.order_product_sequence'),
   name varchar(255) unique not null,
   price numeric not null,
   maximum_preparation_time_in_minutes integer not null
);

create table if not exists fast_order.order_item (
   id integer primary key default nextval('fast_order.order_item_sequence'),
   quantity integer not null
);

-- Sequences own
alter sequence fast_order.order_item_sequence owned by fast_order.order_item.id;
alter sequence fast_order.order_product_sequence owned by fast_order.order_product.id;
alter sequence fast_order.order_status_sequence owned by fast_order.order_status.id;
alter sequence fast_order.order_payment_sequence owned by fast_order.order_payment.id;
alter sequence fast_order.order_sequence owned by fast_order.order.id;

-- Relationships
alter table fast_order.order 
add column order_payment_id integer not null 
constraint order_payment_fk 
references fast_order.order_payment(id);

alter table fast_order.order 
add column order_status_id integer not null 
constraint order_status_id_fk 
references fast_order.order_status(id);

alter table fast_order.order_item
add column order_product_id integer not null 
constraint order_product_id_fk 
references fast_order.order_product(id);

alter table fast_order.order_item
add column order_id integer not null 
constraint order_id_fk 
references fast_order.order(id);
