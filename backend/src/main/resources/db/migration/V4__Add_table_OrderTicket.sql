-- Criando a sequence - OrderTicket
create sequence if not exists fast_order.order_ticket_sq start 1;

-- Criando a tabela - OrderTicket
create table if not exists fast_order.order_ticket (
   id integer primary key default nextval('fast_order.order_ticket_sq'),
   ticket_number integer not null,
   issueTime timestamp not null
);