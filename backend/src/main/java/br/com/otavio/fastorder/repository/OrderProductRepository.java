package br.com.otavio.fastorder.repository;

import br.com.otavio.fastorder.model.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    Optional<OrderProduct> findByName(String name);
}
