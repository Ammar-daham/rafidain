package com.rafidain.tech.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TransactionManager
{
	private SessionFactory sessionFactory;
	
	public TransactionManager(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Starts new hibernate transaction
	 */
	public void  beginTransaction() {
		sessionFactory.getCurrentSession().beginTransaction();
	}
	
	public void commit() {
		sessionFactory.getCurrentSession().getTransaction().commit();
	}
	
	public void shutdown()
	{
		sessionFactory.close();
	}
	
	public void endTransaction()
	{
		Transaction currentTransaction = sessionFactory.getCurrentSession().getTransaction();
		
		if (currentTransaction.isActive())
		{
			currentTransaction.rollback();
		}
		
		sessionFactory.getCurrentSession().close();
	}
}
