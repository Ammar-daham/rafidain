package com.rafidain.tech;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class RestaurantDao
{
	private final SessionFactory sessionFactory;
	private final TransactionManager transactionManager;
	
	public RestaurantDao(TransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
		this.sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	public void shutdown()
	{
		if (sessionFactory != null)
		{
			sessionFactory.close();
		}
	}
}
