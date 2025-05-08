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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "restaurant_id")
	public Long restaurantId;
	
	public String name;
	
	public String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id")
	public Address address;
	
	@ManyToOne
	@JoinColumn(name = "city_id", referencedColumnName = "city_id")
	private City city;
	
	@Column(name = "phone_number")
	public String phoneNumber;
	
	public Boolean isOpen;
	
	public Double rating;
	
	@Column(name = "opening_hours")
	public String openingHours;
	
	@Column(name = "social_media")
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<SocialMedia> socialMedia;
	
	public Long getRestaurantId()
	{
		return restaurantId;
	}
	
	public void setRestaurantId(Long restaurantId)
	{
		this.restaurantId = restaurantId;
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
	
	public Address getAddress()
	{
		return address;
	}
	
	public City getCity()
	{
		return city;
	}
	
	public void setCity(City city)
	{
		this.city = city;
	}
	
	public void setAddress(Address address)
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
	
	public List<SocialMedia> getSocialMedia()
	{
		return socialMedia;
	}
	
	public void setSocialMedia(List<SocialMedia> socialMedia)
	{
		this.socialMedia = socialMedia;
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
	
	@Override
	public String toString()
	{
		return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", description=" + description
				+ ", address=" + address + ", phoneNumber=" + phoneNumber + ", isOpen=" + isOpen + ", rating=" + rating
				+ ", city=" + city
				+ ", openingHours=" + openingHours + ", socialMedia=" + socialMedia + "]";
	}
}
