package br.com.otavio.fastorder.controller;

import br.com.otavio.fastorder.model.dto.OrderProductRecordDTO;
import br.com.otavio.fastorder.model.entity.OrderProduct;
import br.com.otavio.fastorder.service.OrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class OrderProductController {

	private final OrderProductService service;

	public OrderProductController(OrderProductService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public OrderProduct save(@RequestBody OrderProductRecordDTO orderProductDTO) {
		var orderProduct = new OrderProduct();
		BeanUtils.copyProperties(orderProductDTO, orderProduct);
		return service.save(orderProduct);
	}

	@GetMapping
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public List<OrderProduct> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	@Operation(security = @SecurityRequirement(name = "basicAuth"))
	public OrderProduct getById(@PathVariable Integer id) {
		return service.getById(id);
	}
}
