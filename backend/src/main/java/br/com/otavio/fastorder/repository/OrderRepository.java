package br.com.otavio.fastorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.otavio.fastorder.model.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from order o where o.orderTicket.id = :ticketId")
    Optional<Order> getOrderByTicketId(@Param("ticketId") Integer ticketId);
}
