package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.entity.OrderProduct;
import br.com.otavio.fastorder.model.exception.ProductNotFound;
import br.com.otavio.fastorder.repository.OrderProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {
	
	private final OrderProductRepository repository;

	public OrderProductService(OrderProductRepository repository) {
		this.repository = repository;
	}
	
	public OrderProduct save(OrderProduct orderProduct) {
		return repository.save(orderProduct);
	}

	public OrderProduct getById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ProductNotFound("Produto nao encontrado pelo id " + id));
	}

	public OrderProduct getByName(String name) {
		return repository.findByName(name)
				.orElseThrow(() -> new ProductNotFound("Produto nao encontrado pelo nome " + name));
	}

	public List<OrderProduct> getAll() {
		return repository.findAll();
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
