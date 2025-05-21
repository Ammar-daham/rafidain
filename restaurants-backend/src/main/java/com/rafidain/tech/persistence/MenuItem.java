package com.rafidain.tech.persistence;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu_items")
public class MenuItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "menu_item_id")
	private Long menuItemId;
	
	private String name;
	private String description;
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
	private Menu menu;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "menuItem")
	private List<OrderItem> orderItems;
	
	public Long getMenuItemId()
	{
		return menuItemId;
	}
	
	public void setMenuItemId(Long menuItemId)
	{
		this.menuItemId = menuItemId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public Double getPrice()
	{
		return price;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	public Menu getMenu()
	{
		return menu;
	}
	
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
	
	public Restaurant getRestaurant()
	{
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant)
	{
		this.restaurant = restaurant;
	}
	
	public List<OrderItem> getOrderItems()
	{
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems)
	{
		this.orderItems = orderItems;
	}
	
	@Override
	public String toString()
	{
		return "MenuItem [menuItemId=" + menuItemId + ", name=" + name + ", description=" + description + ", price="
				+ price + "]";
	}
	
}

