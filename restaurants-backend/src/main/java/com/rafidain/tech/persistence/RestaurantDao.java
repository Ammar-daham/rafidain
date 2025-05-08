package com.rafidain.tech.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class RestaurantDao
{
	
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
						.addAnnotatedClass(City.class).buildMetadata()
						.buildSessionFactory();
			}
			catch (Throwable ex)
			{
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}
}
