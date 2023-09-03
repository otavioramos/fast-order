package br.com.otavio.fastorder.model.mapper;

import br.com.otavio.fastorder.model.dto.CreateOrderDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderPayment;
import br.com.otavio.fastorder.model.entity.OrderStatus;
import br.com.otavio.fastorder.model.entity.enums.OrderPaymentMethod;
import br.com.otavio.fastorder.model.entity.enums.OrderStatusDescription;

import java.time.LocalDateTime;
import java.util.function.Function;

public class OrderMapper implements Function<CreateOrderDTO, Order> {

    @Override
    public Order apply(CreateOrderDTO createOrderDTO) {
        var order = new Order();
        order.setCreationTime(LocalDateTime.now());

        var payment = new OrderPayment();
        payment.setPaymentMethod(OrderPaymentMethod.valueOf(createOrderDTO.payment().method()));
        payment.setInstallments(createOrderDTO.payment().installments());
        order.setPayment(payment);

        var orderStatus = new OrderStatus();
        orderStatus.setCurrentStatus(OrderStatusDescription.WAITING_FOR_PREPARATION);
        order.setOrderStatus(orderStatus);

        return order;
    }
}
