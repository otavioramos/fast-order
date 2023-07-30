package br.com.otavio.fastorder.controller;

import br.com.otavio.fastorder.model.dto.OrderProductRecordDTO;
import br.com.otavio.fastorder.model.entity.OrderProduct;
import br.com.otavio.fastorder.service.OrderProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class OrderProductController {

	private final OrderProductService service;

	public OrderProductController(OrderProductService service) {
		super();
		this.service = service;
	}

	@PostMapping
	public OrderProduct save(@RequestBody OrderProductRecordDTO orderProductDTO) {
		var orderProduct = new OrderProduct();
		BeanUtils.copyProperties(orderProductDTO, orderProduct);
		return service.save(orderProduct);
	}
}
