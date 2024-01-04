package br.com.otavio.fastorder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "orderItem")
@Table(name = "order_item", schema = "fast_order")
public class OrderItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sq")
	@SequenceGenerator(name = "order_item_sq", sequenceName = "fast_order.order_item_sequence", allocationSize = 1)
	private Integer id;
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "order_product_id", referencedColumnName = "id", nullable = false)
	private OrderProduct product;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@Column(name = "quantity")
	private Integer quantity;

	public OrderItem(Integer id, OrderProduct product, Integer quantity) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}

	public OrderItem() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderProduct getProduct() {
		return product;
	}

	public void setProduct(OrderProduct product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getTotal() {
		return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, product, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(product, other.product)
				&& Objects.equals(quantity, other.quantity);
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}
}
