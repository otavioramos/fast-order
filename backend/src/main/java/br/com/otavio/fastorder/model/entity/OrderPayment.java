package br.com.otavio.fastorder.model.entity;

import java.math.BigDecimal;
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

@Entity(name = "orderPayment")
@Table(name = "order_payment", schema = "fast_order")
public class OrderPayment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_payment_sq")
	@SequenceGenerator(name = "order_payment_sq", sequenceName = "order_payment_sequence", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private OrderPaymentMethod paymentMethod;
	
	@Column(name = "total", nullable = false)
	private BigDecimal total;
	
	@Column(name = "installments", nullable = false)
	private Integer installments;
	
	public OrderPayment(Integer id, OrderPaymentMethod paymentMethod, BigDecimal total, Integer installments) {
		super();
		this.id = id;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.installments = installments;
	}
	
	public OrderPayment() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderPaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(OrderPaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, installments, paymentMethod, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderPayment other = (OrderPayment) obj;
		return Objects.equals(id, other.id) && Objects.equals(installments, other.installments)
				&& paymentMethod == other.paymentMethod && Objects.equals(total, other.total);
	}

	@Override
	public String toString() {
		return "OrderPayment [id=" + id + ", paymentMethod=" + paymentMethod + ", total=" + total + ", installments="
				+ installments + "]";
	}
}
