package br.com.otavio.fastorder.controller;

import br.com.otavio.fastorder.model.dto.OrderRecordDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	private final OrderService service;

	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@PostMapping
	public Order save(@RequestBody OrderRecordDTO orderDTO) {
		return service.save(orderDTO);
	}
}
