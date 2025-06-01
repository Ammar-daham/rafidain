package com.rafidain.tech.persistence;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("name")
	public FinnishCity name;
	
	@OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
	@JsonManagedReference("restaurant-city")
	private List<Restaurant> restaurants = new ArrayList<>();
	

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
	
	public void setName(FinnishCity name)
	{
		this.name = name;
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
