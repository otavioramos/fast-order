package br.com.otavio.fastorder.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "order")
@Table(name = "order", schema = "fast_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sq")
    @SequenceGenerator(name = "order_sq", sequenceName = "order_sequence", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "creation_time",  nullable = false)
    private LocalDateTime creationTime;

    @OneToOne
    @JoinColumn(name = "order_payment_id", referencedColumnName = "id", nullable = false)
    private OrderPayment payment;

    @OneToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

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

	@Override
	public int hashCode() {
		return Objects.hash(creationTime, id, items, orderStatus, payment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(creationTime, other.creationTime) && Objects.equals(id, other.id)
				&& Objects.equals(items, other.items) && Objects.equals(orderStatus, other.orderStatus)
				&& Objects.equals(payment, other.payment);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", creationTime=" + creationTime + ", payment=" + payment + ", orderStatus="
				+ orderStatus + ", items=" + items + "]";
	}
}
