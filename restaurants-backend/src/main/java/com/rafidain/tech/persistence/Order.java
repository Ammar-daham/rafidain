package com.rafidain.tech.persistence;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long orderId;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "user_id")
	private User customer;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
	private Restaurant restaurant;
	
	private Double totalPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;
	
	public Long getOrderId()
	{
		return orderId;
	}
	
	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}
	
	public User getCustomer()
	{
		return customer;
	}
	
	public void setCustomer(User customer)
	{
		this.customer = customer;
	}
	
	public Restaurant getRestaurant()
	{
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant)
	{
		this.restaurant = restaurant;
	}
	
	public Double getTotalPrice()
	{
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	
	public OrderStatus getStatus()
	{
		return status;
	}
	
	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}
	
	public List<OrderItem> getOrderItems()
	{
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems)
	{
		this.orderItems = orderItems;
	}
}