package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.dto.OrderItemRecordDTO;
import br.com.otavio.fastorder.model.entity.OrderItem;
import org.springframework.stereotype.Service;

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
            var orderProduct = orderProductService.load(item.productId());
            var orderItem = new OrderItem();
            orderItem.setQuantity(item.quantity());
            orderItem.setProduct(orderProduct);
            items.add(orderItem);
        });

        return items;
    }
}
