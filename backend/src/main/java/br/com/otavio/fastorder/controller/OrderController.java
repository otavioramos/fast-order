package br.com.otavio.fastorder.controller;

import br.com.otavio.fastorder.model.dto.CreateOrderDTO;
import br.com.otavio.fastorder.model.dto.UpdateOrderDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderPayment;
import br.com.otavio.fastorder.model.entity.OrderStatus;
import br.com.otavio.fastorder.model.entity.OrderTicket;
import br.com.otavio.fastorder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public Order save(@RequestBody CreateOrderDTO orderDTO) {
		return service.save(orderDTO);
	}

	@GetMapping
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public List<Order> get(
			@RequestParam(value = "ticket", required = false) Integer ticketNumber) {
		if (ticketNumber != null)
			return Collections.singletonList(service.getByTicket(ticketNumber));
		else
			return service.getAll();
	}

	@GetMapping("/{id}")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public Order getById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@GetMapping("/{id}/status")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public OrderStatus getStatusById(@PathVariable Integer id) {
		return service.getById(id).getOrderStatus();
	}

	@GetMapping("/{id}/payment")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public OrderPayment getPaymentById(@PathVariable Integer id) {
		return service.getById(id).getPayment();
	}

	@GetMapping("/{id}/ticket")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public OrderTicket getTicketById(@PathVariable Integer id) {
		return service.getById(id).getOrderTicket();
	}

	@PutMapping("/{id}")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public Order update(@PathVariable Integer id,
						@RequestBody UpdateOrderDTO updateOrderDTO) {
		return service.update(id, updateOrderDTO);
	}
}
