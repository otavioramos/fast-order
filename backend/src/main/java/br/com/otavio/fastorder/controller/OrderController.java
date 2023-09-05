package br.com.otavio.fastorder.controller;

import br.com.otavio.fastorder.model.dto.CreateOrderDTO;
import br.com.otavio.fastorder.model.dto.UpdateOrderDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderPayment;
import br.com.otavio.fastorder.model.entity.OrderStatus;
import br.com.otavio.fastorder.model.entity.OrderTicket;
import br.com.otavio.fastorder.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	private final OrderService service;

	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@PostMapping
	public Order save(@RequestBody CreateOrderDTO orderDTO) {
		return service.save(orderDTO);
	}

	@GetMapping
	public List<Order> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Order getById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@GetMapping("/{id}/status")
	public OrderStatus getStatusById(@PathVariable Integer id) {
		return service.getById(id).getOrderStatus();
	}

	@GetMapping("/{id}/payment")
	public OrderPayment getPaymentById(@PathVariable Integer id) {
		return service.getById(id).getPayment();
	}

	@GetMapping("/{id}/ticket")
	public OrderTicket getTicketById(@PathVariable Integer id) {
		return service.getById(id).getOrderTicket();
	}

	@PutMapping("/{id}")
	public Order update(@PathVariable Integer id,
						@RequestBody UpdateOrderDTO updateOrderDTO) {
		return service.update(id, updateOrderDTO);
	}
}
