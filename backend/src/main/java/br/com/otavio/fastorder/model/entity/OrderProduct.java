package br.com.otavio.fastorder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "orderProduct")
@Table(name = "order_product", schema = "fast_order")
public class OrderProduct {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_sq")
	@SequenceGenerator(name = "order_product_sq", sequenceName = "fast_order.order_product_sequence", allocationSize = 1)
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@Column(name = "maximum_preparation_time_in_minutes")
	private Integer maximumPreparationTimeInMinutes;

	public OrderProduct(Integer id, String name, BigDecimal price, Integer maximumPreparationTimeInMinutes) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.maximumPreparationTimeInMinutes = maximumPreparationTimeInMinutes;
	}

	public OrderProduct() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getMaximumPreparationTimeInMinutes() {
		return maximumPreparationTimeInMinutes;
	}

	public void setMaximumPreparationTimeInMinutes(Integer maximumPreparationTimeInMinutes) {
		this.maximumPreparationTimeInMinutes = maximumPreparationTimeInMinutes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, maximumPreparationTimeInMinutes, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProduct other = (OrderProduct) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(maximumPreparationTimeInMinutes, other.maximumPreparationTimeInMinutes)
				&& Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", name=" + name + ", price=" + price + ", maximumPreparationTimeInMinutes="
				+ maximumPreparationTimeInMinutes + "]";
	}
}
