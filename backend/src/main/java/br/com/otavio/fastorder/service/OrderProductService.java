package br.com.otavio.fastorder.service;

import org.springframework.stereotype.Service;

import br.com.otavio.fastorder.model.entity.OrderProduct;
import br.com.otavio.fastorder.repository.OrderProductRepository;

@Service
public class OrderProductService {
	
	private final OrderProductRepository repository;

	public OrderProductService(OrderProductRepository repository) {
		super();
		this.repository = repository;
	}
	
	public OrderProduct save(OrderProduct orderProduct) {
		return repository.save(orderProduct);
	}

	public OrderProduct load(Integer id) {
		return repository.getReferenceById(id);
	}
}
