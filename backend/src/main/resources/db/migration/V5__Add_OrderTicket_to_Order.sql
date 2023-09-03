-- Criando a relação de OrderTicket com Order
alter table fast_order.order
add column order_ticket_id integer not null
constraint order_ticket_id_fk
references fast_order.order_ticket(id);