package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.controller.mapper.OrderMapper;
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
	private static final OrderMapper ORDER_MAPPER = new OrderMapper();

	public OrderService(OrderRepository repository, OrderItemService orderItemService) {
		super();
		this.repository = repository;
		this.orderItemService = orderItemService;
	}
	
	public Order save(OrderRecordDTO orderDTO) {
		var order = ORDER_MAPPER.apply(orderDTO);
		List<OrderItem> items = orderItemService.loadItems(orderDTO.items());
		order.setItems(items);
		items.forEach(item -> item.setOrder(order));

		BigDecimal total = order.getItems().stream()
				.map(OrderItem::getTotal)
				.reduce(BigDecimal.valueOf(0), BigDecimal::add);
		order.getPayment().setTotal(total);

		Integer maxMinutesToPrepare = order.getItems().stream()
				.map(item -> item.getProduct().getMaximumPreparationTimeInMinutes() * item.getQuantity())
				.max(Integer::compareTo)
				.orElseThrow(() -> new RuntimeException("Nao foi possivel obter o tempo maximo de preparacao em minutos"));
		order.getOrderStatus().setDeadline(order.getCreationTime().plusMinutes(maxMinutesToPrepare));

		return repository.save(order);
	}

	public List<Order> getAll() {
		return repository.findAll();
	}
}
