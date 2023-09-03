package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.entity.OrderTicket;
import br.com.otavio.fastorder.repository.OrderTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderTicketService {

    private final OrderTicketRepository repository;

    public OrderTicketService(OrderTicketRepository repository) {
        this.repository = repository;
    }

    private boolean isTicketNumberExceeded(Integer ticketNumber) {
        return ticketNumber >= 99999;
    }

    public OrderTicket generate() {
        Integer maxTicketNumber = repository.getMaxTicketNumberForToday().orElse(0);

        if (this.isTicketNumberExceeded(maxTicketNumber)) {
            throw new RuntimeException("Numero de senhas excedido por hoje");
        }

        var orderTicket = new OrderTicket();
        orderTicket.setTicketNumber(maxTicketNumber + 1);
        orderTicket.setIssueTime(LocalDateTime.now());

        return orderTicket;
    }
}
