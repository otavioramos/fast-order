package br.com.otavio.fastorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.otavio.fastorder.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
