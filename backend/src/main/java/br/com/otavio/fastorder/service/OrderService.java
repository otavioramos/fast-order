package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.dto.CreateOrderDTO;
import br.com.otavio.fastorder.model.dto.UpdateOrderDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderItem;
import br.com.otavio.fastorder.model.entity.OrderTicket;
import br.com.otavio.fastorder.model.entity.enums.OrderStatusDescription;
import br.com.otavio.fastorder.model.exception.OrderNotFound;
import br.com.otavio.fastorder.model.mapper.OrderMapper;
import br.com.otavio.fastorder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

	private final OrderRepository repository;
	private final OrderItemService orderItemService;
	private final OrderStatusService orderStatusService;
	private static final OrderMapper ORDER_MAPPER = new OrderMapper();

	public OrderService(OrderRepository repository,
						OrderItemService orderItemService,
						OrderStatusService orderStatusService) {
		super();
		this.repository = repository;
		this.orderItemService = orderItemService;
		this.orderStatusService = orderStatusService;
	}

	public Order save(CreateOrderDTO orderDTO) {
		var order = ORDER_MAPPER.apply(orderDTO);
		List<OrderItem> items = orderItemService.loadItems(orderDTO.items());
		order.setItems(items);
		items.forEach(item -> item.setOrder(order));

		BigDecimal total = this.orderItemService.getTotal(order.getItems());
		order.getPayment().setTotal(total);

		OrderTicket ticket = new OrderTicket();
		ticket.setOrder(order);
		ticket.setIssueTime(LocalDateTime.now());
		order.setOrderTicket(ticket);

		Integer totalMinutesToPrepare = this.orderStatusService.getTotalMinutesToPrepare(order.getItems());
		order.getOrderStatus().setDeadline(order.getCreationTime().plusMinutes(totalMinutesToPrepare));

		return repository.save(order);
	}

	public List<Order> getAll() {
		return repository.findAll();
	}

	public Order getById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new OrderNotFound("Pedido nao encontrado pelo id " + id));
	}

	public Order getByTicket(Integer ticketId) {
		return repository.getOrderByTicketId(ticketId)
				.orElseThrow(() -> new OrderNotFound("Pedido nao encontrado pelo ticketId " + ticketId));
	}

	public Order update(Integer orderId, UpdateOrderDTO dto) {
		var order = this.getById(orderId);

		OrderStatusDescription desiredStatus = OrderStatusDescription.valueOf(dto.status());
		order.getOrderStatus().setCurrentStatus(desiredStatus);

		return repository.save(order);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
