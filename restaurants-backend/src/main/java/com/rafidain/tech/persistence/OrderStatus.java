package com.rafidain.tech.persistence;

public enum OrderStatus
{
	PENDING,      // Order is placed but not confirmed
	CONFIRMED,    // Restaurant has confirmed the order
	PREPARING,    // Order is being prepared
	READY,        // Order is ready for pickup
	OUT_FOR_DELIVERY, // Order is with the courier
	DELIVERED,    // Order has been delivered
	CANCELLED     // Order was cancelled
}
