package br.com.otavio.fastorder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "orderTicket")
@Table(name = "order_ticket", schema = "fast_order")
public class OrderTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_ticket_sq")
    @SequenceGenerator(name = "order_ticket_sq", sequenceName = "fast_order.order_ticket_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "ticket_number", nullable = false, length = 5)
    private Integer ticketNumber;

    @Column(name = "issue_time", nullable = false)
    private LocalDateTime issueTime;

    @OneToOne(mappedBy = "orderTicket", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    public OrderTicket(Integer id, Integer ticketNumber, LocalDateTime issueTime, LocalDateTime expirationTime, Order order) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.issueTime = issueTime;
        this.order = order;
    }

    public OrderTicket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTicket that = (OrderTicket) o;
        return Objects.equals(id, that.id) && Objects.equals(ticketNumber, that.ticketNumber) && Objects.equals(issueTime, that.issueTime) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketNumber, issueTime, order);
    }

    @Override
    public String toString() {
        return "OrderTicket{" +
                "id=" + id +
                ", ticketNumber=" + ticketNumber +
                ", issueTime=" + issueTime +
                '}';
    }
}
