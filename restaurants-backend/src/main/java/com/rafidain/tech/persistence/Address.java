package com.rafidain.tech.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Address
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id")
	public Long addressId;
	
	@Column(name = "street_name")
	public String streetName;
	
	public String postalCode;
	
	@OneToOne(mappedBy = "address")
	public Restaurant restaurant;
	
	public Long getAddressId()
	{
		return addressId;
	}
	
	public void setAddressId(Long addressId)
	{
		this.addressId = addressId;
	}
	
	public String getStreetName()
	{
		return streetName;
	}
	
	public void setStreetName(String streetName)
	{
		this.streetName = streetName;
	}
	
	public String getPostalCode()
	{
		return postalCode;
	}
	
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}
	
	public Restaurant getRestaurant()
	{
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant)
	{
		this.restaurant = restaurant;
	}
	
	@Override
	public String toString()
	{
		return "Address [addressId=" + addressId + ", streetName=" + streetName + ", postalCode=" + postalCode + "]";
	}
	
}
