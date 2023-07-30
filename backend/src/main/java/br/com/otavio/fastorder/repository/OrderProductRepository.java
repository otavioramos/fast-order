package br.com.otavio.fastorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.otavio.fastorder.model.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}
