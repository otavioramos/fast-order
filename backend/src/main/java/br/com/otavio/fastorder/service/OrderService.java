package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.mapper.OrderMapper;
import br.com.otavio.fastorder.model.dto.OrderRecordDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderItem;
import br.com.otavio.fastorder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

	private final OrderRepository repository;
	private final OrderItemService orderItemService;
	private final OrderStatusService orderStatusService;
	private static final OrderMapper ORDER_MAPPER = new OrderMapper();

	public OrderService(OrderRepository repository, OrderItemService orderItemService, OrderStatusService orderStatusService) {
		super();
		this.repository = repository;
		this.orderItemService = orderItemService;
		this.orderStatusService = orderStatusService;
	}
	
	public Order save(OrderRecordDTO orderDTO) {
		var order = ORDER_MAPPER.apply(orderDTO);
		List<OrderItem> items = orderItemService.loadItems(orderDTO.items());
		order.setItems(items);
		items.forEach(item -> item.setOrder(order));

		BigDecimal total = this.orderItemService.getTotal(order.getItems());
		order.getPayment().setTotal(total);

		Integer totalMinutesToPrepare = this.orderStatusService.getTotalMinutesToPrepare(order.getItems());
		order.getOrderStatus().setDeadline(order.getCreationTime().plusMinutes(totalMinutesToPrepare));

		return repository.save(order);
	}

	public List<Order> getAll() {
		return repository.findAll();
	}
}
