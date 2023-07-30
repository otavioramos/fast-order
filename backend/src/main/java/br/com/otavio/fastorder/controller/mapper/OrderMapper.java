package br.com.otavio.fastorder.controller.mapper;

import br.com.otavio.fastorder.model.dto.OrderRecordDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderPayment;
import br.com.otavio.fastorder.model.entity.OrderStatus;
import br.com.otavio.fastorder.model.entity.enums.OrderPaymentMethod;
import br.com.otavio.fastorder.model.entity.enums.OrderStatusDescription;

import java.time.LocalDateTime;
import java.util.function.Function;

public class OrderMapper implements Function<OrderRecordDTO, Order> {

    @Override
    public Order apply(OrderRecordDTO orderRecordDTO) {
        var order = new Order();
        order.setCreationTime(LocalDateTime.now());

        var payment = new OrderPayment();
        payment.setPaymentMethod(OrderPaymentMethod.valueOf(orderRecordDTO.payment().method()));
        payment.setInstallments(orderRecordDTO.payment().installments());
        order.setPayment(payment);

        var orderStatus = new OrderStatus();
        orderStatus.setCurrentStatus(OrderStatusDescription.WAITING_FOR_PREPARATION);
        order.setOrderStatus(orderStatus);

        return order;
    }
}
