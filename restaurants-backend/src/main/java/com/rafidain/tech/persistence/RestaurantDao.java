package com.rafidain.tech.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class RestaurantDao
{
	private TransactionManager transactionManager;
	public RestaurantDao(TransactionManager transactionManger)
	{
		this.transactionManager = transactionManger;
	}
	
	public RestaurantDao()
	{
	}
	
	public SessionFactory buildSessionFactory()
	{
		SessionFactory sessionFactory = null;
		final StandardServiceRegistry registery = new StandardServiceRegistryBuilder().configure().build();
		if (sessionFactory == null)
		{
			try
			{
				sessionFactory = new MetadataSources(registery).addAnnotatedClass(Restaurant.class)
						.addAnnotatedClass(SocialMedia.class).addAnnotatedClass(Address.class)
						.addAnnotatedClass(City.class).addAnnotatedClass(User.class).addAnnotatedClass(Menu.class)
						.addAnnotatedClass(MenuItem.class).addAnnotatedClass(Order.class)
						.addAnnotatedClass(OrderItem.class).buildMetadata()
						.buildSessionFactory();
			}
			catch (Throwable ex)
			{
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}
	
	public void createRestaurant(Restaurant restaurant)
	{
		try
		{
			transactionManager.getCurrentSession().save(restaurant);
			System.out.println("Restaurant saved successfully: " + restaurant.getName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error saving restaurant.");
		}
	}
	
	public void addMenu(Long restaurantId, Menu menu)
	{
		try
		{
			// Fetch the restaurant from the database
			Restaurant restaurant = transactionManager.getCurrentSession().get(Restaurant.class, restaurantId);
			if (restaurant == null)
			{
				throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found.");
			}
			
			// Associate the menu with the restaurant
			menu.setRestaurant(restaurant);
			transactionManager.getCurrentSession().save(menu);
			
			System.out.println("Menu added successfully to restaurant: " + restaurant.getName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error adding menu to restaurant.");
		}
	}
	
	public void addMenuItem(Long restaurantId, Long menuId, MenuItem menuItem)
	{
		try
		{
			
			// Fetch the restaurant and menu from the database
			Restaurant restaurant = transactionManager.getCurrentSession().get(Restaurant.class, restaurantId);
			Menu menu = transactionManager.getCurrentSession().get(Menu.class, menuId);
			
			if (restaurant == null)
			{
				throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found.");
			}
			if (menu == null)
			{
				throw new IllegalArgumentException("Menu with ID " + menuId + " not found.");
			}
			
			// Associate the menu item with the menu and restaurant
			menuItem.setMenu(menu);
			menuItem.setRestaurant(restaurant);
			transactionManager.getCurrentSession().save(menuItem);
			System.out.println("Menu item added successfully to menu: " + menu.getName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error adding menu item.");
		}
	}
	
}
