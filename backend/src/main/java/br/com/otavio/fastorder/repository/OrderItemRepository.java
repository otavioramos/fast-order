package br.com.otavio.fastorder.repository;

import br.com.otavio.fastorder.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
