package com.rafidain.tech.persistence;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "menus")
public class Menu
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "menu_id")
	private Long menuId;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
	private Restaurant restaurant;
	
	private String name; // Example: "Lunch Menu", "Dinner Specials"
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private List<MenuItem> menuItems;
}
