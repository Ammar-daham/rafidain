package com.rafidain.tech;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionManager
{
	private static final ThreadLocal<Transaction> threadLocalTransaction = new ThreadLocal<>();
	
	public void beginTransaction(Session session)
	{
		if (session != null && !session.getTransaction().isActive())
		{
			threadLocalTransaction.set(session.beginTransaction());
		}
	}
	
	public void commitTransaction()
	{
		Transaction tx = threadLocalTransaction.get();
		if (tx != null && tx.isActive())
		{
			try
			{
				tx.commit();
			}
			catch (Exception e)
			{
				rollbackTransaction();
				throw e;
			}
			finally
			{
				threadLocalTransaction.remove();
			}
		}
	}
	
	public void rollbackTransaction()
	{
		Transaction tx = threadLocalTransaction.get();
		if (tx != null && tx.isActive())
		{
			tx.rollback();
			threadLocalTransaction.remove();
		}
	}
}
