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
}

