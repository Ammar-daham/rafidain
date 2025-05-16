package com.rafidain.tech.persistence;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cities")
public class City
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id")
	public Long cityId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "name", nullable = false, unique = true)
	public FinnishCity name;
	
	@OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
	public List<Restaurant> restaurants;
	
	public Long getCityId()
	{
		return cityId;
	}
	
	public void setCityId(Long cityId)
	{
		this.cityId = cityId;
	}
	
	public FinnishCity getName()
	{
		return name;
	}
	
	public void setName(FinnishCity cityEnum)
	{
		this.name = cityEnum;
	}
	
	public List<Restaurant> getRestaurants()
	{
		return restaurants;
	}
	
	public void setRestaurants(List<Restaurant> restaurants)
	{
		this.restaurants = restaurants;
	}

	@Override
	public String toString()
	{
		return "City [cityId=" + cityId + ", name=" + name + ", restaurants=" + restaurants + "]";
	}

	
	
}
