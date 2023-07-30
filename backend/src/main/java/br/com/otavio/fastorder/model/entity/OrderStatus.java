package br.com.otavio.fastorder.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "orderStatus")
@Table(name = "order_status", schema = "fast_order")
public class OrderStatus {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_sq")
	@SequenceGenerator(name = "order_status_sq", sequenceName = "order_status_sequence", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "current_status", nullable = false)
	private OrderStatusDescription currentStatus;
	
	@Column(name = "deadline", nullable = false)
	private LocalDateTime deadline;

	public OrderStatus(Integer id, OrderStatusDescription currentStatus, LocalDateTime deadline) {
		super();
		this.id = id;
		this.currentStatus = currentStatus;
		this.deadline = deadline;
	}

	public OrderStatus() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderStatusDescription getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(OrderStatusDescription currentStatus) {
		this.currentStatus = currentStatus;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentStatus, deadline, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStatus other = (OrderStatus) obj;
		return currentStatus == other.currentStatus && Objects.equals(deadline, other.deadline)
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderStatus [id=" + id + ", currentStatus=" + currentStatus + ", deadline=" + deadline + "]";
	}
}
