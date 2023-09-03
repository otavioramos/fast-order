package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.dto.OrderItemRecordDTO;
import br.com.otavio.fastorder.model.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderProductService orderProductService;

    public OrderItemService(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    public List<OrderItem> loadItems(List<OrderItemRecordDTO> itemsDTOs) {
        List<OrderItem> items = new ArrayList<>();
        itemsDTOs.forEach(item -> {
            var orderProduct = orderProductService.getById(item.productId());
            var orderItem = new OrderItem();
            orderItem.setQuantity(item.quantity());
            orderItem.setProduct(orderProduct);
            items.add(orderItem);
        });

        return items;
    }

    public BigDecimal getTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}
