package br.com.otavio.fastorder.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "order")
@Table(name = "order", schema = "fast_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sq")
    @SequenceGenerator(name = "order_sq", sequenceName = "fast_order.order_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "creation_time",  nullable = false)
    private LocalDateTime creationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_payment_id", referencedColumnName = "id", nullable = false)
    private OrderPayment payment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_status_id", referencedColumnName = "id", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_ticket_id", referencedColumnName = "id", nullable = false)
	private OrderTicket orderTicket;

	public Order(Integer id, LocalDateTime creationTime, OrderPayment payment, OrderStatus orderStatus,
			List<OrderItem> items) {
		super();
		this.id = id;
		this.creationTime = creationTime;
		this.payment = payment;
		this.orderStatus = orderStatus;
		this.items = items;
	}

	public Order() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public OrderPayment getPayment() {
		return payment;
	}

	public void setPayment(OrderPayment payment) {
		this.payment = payment;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public OrderTicket getOrderTicket() {
		return orderTicket;
	}

	public void setOrderTicket(OrderTicket orderTicket) {
		this.orderTicket = orderTicket;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id) && Objects.equals(creationTime, order.creationTime) && Objects.equals(payment, order.payment) && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(items, order.items) && Objects.equals(orderTicket, order.orderTicket);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, creationTime, payment, orderStatus, items, orderTicket);
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", creationTime=" + creationTime +
				", payment=" + payment +
				", orderStatus=" + orderStatus +
				", items=" + items +
				", orderTicket=" + orderTicket +
				'}';
	}
}
