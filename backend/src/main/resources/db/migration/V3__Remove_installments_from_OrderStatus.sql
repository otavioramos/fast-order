-- Remove 'installments' from fast_order.order_status
alter table fast_order.order_status
drop column installments;