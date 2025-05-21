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
						.addAnnotatedClass(City.class).addAnnotatedClass(User.class).buildMetadata()
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
	
}
