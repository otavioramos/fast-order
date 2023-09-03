package br.com.otavio.fastorder.repository;

import br.com.otavio.fastorder.model.entity.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderTicketRepository extends JpaRepository<OrderTicket, Integer> {

    @Query("select max(ot.ticketNumber) " +
            "from orderTicket ot " +
            "where DATE(issueTime) = CURRENT_DATE")
    Optional<Integer> getMaxTicketNumberForToday();
}
