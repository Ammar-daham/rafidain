package com.rafidain.tech.persistence;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long restaurantId;
	
	public String name;
	
	public String description;
	
	public String address;
	
	@Column(name = "phone_number")
	public String phoneNumber;
	
	public Boolean isOpen;
	
	public Double rating;
	
	@Column(name = "opening_hours")
	public String openingHours;
	
	@Column(name = "social_media")
	public List<String> socialMedia;
	
	public Long getRestaurantId()
	{
		return restaurantId;
	}
	
	public void setRestaurantId(Long restaurantId)
	{
		this.restaurantId = restaurantId;
	}
	
	public Double getRating()
	{
		return rating;
	}
	
	public void setRating(Double rating)
	{
		this.rating = rating;
	}
	
	public String getOpeningHours()
	{
		return openingHours;
	}
	
	public void setOpeningHours(String openingHours)
	{
		this.openingHours = openingHours;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public Boolean getIsOpen()
	{
		return isOpen;
	}
	
	public void setIsOpen(Boolean isOpen)
	{
		this.isOpen = isOpen;
	}
}
