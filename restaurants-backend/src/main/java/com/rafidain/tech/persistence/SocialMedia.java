package com.rafidain.tech.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SocialMedia
{
	@Id
	@GeneratedValue
	@Column(name = "social_media_id")
	private Long socialMediaId;
	
	private String platform;
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	@JsonBackReference("restaurant-socialMedia")
	private Restaurant restaurant;
	
	public Long getSocialMediaId()
	{
		return socialMediaId;
	}
	
	public void setSocialMediaId(Long socialMediaId)
	{
		this.socialMediaId = socialMediaId;
	}
	
	public String getPlatform()
	{
		return platform;
	}
	
	public void setPlatform(String platform)
	{
		this.platform = platform;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
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
		return "SocialMedia [socialMediaId=" + socialMediaId + ", platform=" + platform + ", url=" + url + "]";
	}

}
