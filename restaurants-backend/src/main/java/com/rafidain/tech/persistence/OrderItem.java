package com.rafidain.tech.persistence;

import java.awt.MenuItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_item_id")
	private Long orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "menu_item_id", referencedColumnName = "menu_item_id")
	//private MenuItem menuItem;
	
	private int quantity;
	private double price;
	
	// Constructors
	public OrderItem()
	{
	}
	
	public OrderItem(Order order, MenuItem menuItem, int quantity, double price)
	{
		this.order = order;
		//this.menuItem = menuItem;
		this.quantity = quantity;
		this.price = price;
	}
	
	// Getters and Setters
	public Long getOrderItemId()
	{
		return orderItemId;
	}
	
	public void setOrderItemId(Long orderItemId)
	{
		this.orderItemId = orderItemId;
	}
	
	public Order getOrder()
	{
		return order;
	}
	
	public void setOrder(Order order)
	{
		this.order = order;
	}
	
	//	public MenuItem getMenuItem()
	//	{
	//		return menuItem;
	//	}
	//	
	//	public void setMenuItem(MenuItem menuItem)
	//	{
	//		this.menuItem = menuItem;
	//	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
}

