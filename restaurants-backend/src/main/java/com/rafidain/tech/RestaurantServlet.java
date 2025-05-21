package com.rafidain.tech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafidain.tech.persistence.Address;
import com.rafidain.tech.persistence.City;
import com.rafidain.tech.persistence.FinnishCity;
import com.rafidain.tech.persistence.Restaurant;
import com.rafidain.tech.persistence.RestaurantDao;
import com.rafidain.tech.persistence.SocialMedia;
import com.rafidain.tech.persistence.TransactionManager;

@WebServlet(name = "Restaurants", urlPatterns = "/*", loadOnStartup = 1)
public class RestaurantServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private RestaurantDao restaurantDao;
	private TransactionManager transactionManager;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		restaurantDao = new RestaurantDao();
		transactionManager = new TransactionManager(restaurantDao.buildSessionFactory());
		restaurantDao = new RestaurantDao(transactionManager);
		System.out.println("RestaurantDao initialized and DB connection tested.");
		//		transactionManager.beginTransaction();
		//		try
		//		{
		//			
		//			// Retrieve an existing city from the database
		//			City city =
		//					transactionManager.getCurrentSession().createQuery("FROM City WHERE name = :cityName", City.class)
		//							.setParameter("cityName", FinnishCity.HELSINKI) // Example: Helsinki
		//							.getSingleResult();
		//			
		//			System.out.println(city.toString());
		//			
		//			// Create a new restaurant and associate it with an existing city
		//			Restaurant res = new Restaurant();
		//			res.setName("Kotkot");
		//			res.setDescription("Delicious grilled chicken");
		//			res.setIsOpen(true);
		//			res.setPhoneNumber("00000");
		//			res.setOpeningHours("10:00 - 22:00");
		//			res.setRating(4.5);
		//			res.setCity(city); // Associate the restaurant with the retrieved city
		//			
		//			// Create Address
		//			Address address = new Address();
		//			address.setPostalCode("00560");
		//			address.setStreetName("Hämeentie 109");
		//			res.setAddress(address); // Associate address with the restaurant
		//			
		//			// Create Social Media entries
		//			SocialMedia instagram = new SocialMedia();
		//			instagram.setPlatform("Instagram");
		//			instagram.setUrl("https://instagram.com/kotkot");
		//			instagram.setRestaurant(res);  // Link to restaurant
		//			
		//			SocialMedia facebook = new SocialMedia();
		//			facebook.setPlatform("Facebook");
		//			facebook.setUrl("https://facebook.com/kotkot");
		//			facebook.setRestaurant(res);  // Link to restaurant
		//			
		//			// Add social media entries to the restaurant
		//			List<SocialMedia> socialMediaList = new ArrayList<>();
		//			socialMediaList.add(instagram);
		//			socialMediaList.add(facebook);
		//			res.setSocialMedia(socialMediaList);
		//			
		//			transactionManager.getCurrentSession().save(res); // Save the restaurant
		//			
		//			transactionManager.commit();
		//			System.out.println("Restaurant saved successfully in city: " + city.getName());
		//			
		//		}
		//		catch (Exception e)
		//		{
		//			e.printStackTrace();
		//		}
		//		finally
		//		{
		//			transactionManager.endTransaction();
		//		}
	}
	
	@Override
	public void destroy()
	{
		if (restaurantDao != null)
		{
			transactionManager.shutdown();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/plain");
		response.getWriter().println("Hello from RestaurantServlet!");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json");
		
		transactionManager.beginTransaction(); // Begin transaction inside doPost
		
		try
		{
			String action = request.getParameter("action");
			
			switch (action)
			{
				case "createRestaurant":
					createRestaurant(request, response);
					break;
				default:
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write("{\"message\": \"Invalid action type.\"}");
					break;
			}
			
			transactionManager.commit(); // Commit transaction
		}
		catch (Exception e)
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\": \"Error processing request.\"}");
			e.printStackTrace();
		}
		finally
		{
			transactionManager.endTransaction(); // End transaction safely
		}
	}
	
	private void createRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			// Read JSON from request body
			ObjectMapper objectMapper = new ObjectMapper();
			Restaurant restaurant = objectMapper.readValue(request.getInputStream(), Restaurant.class);
			// Create Restaurant entity
			restaurant.setName(restaurant.getName());
			restaurant.setDescription(restaurant.getDescription());
			restaurant.setPhoneNumber(restaurant.getPhoneNumber());
			restaurant.setOpeningHours(restaurant.getOpeningHours());
			restaurant.setIsOpen(restaurant.getIsOpen());
			restaurant.setRating(restaurant.getRating());
			
			FinnishCity cityName = restaurant.getCity().getName();
			System.out.println("cityName " + cityName);
			
			// Fetch city from DB
			City city =
					transactionManager.getCurrentSession().createQuery("FROM City WHERE name = :cityName", City.class)
							.setParameter("cityName", cityName).getSingleResult();
			restaurant.setCity(city);
			
			// Create Address object
			Address address = new Address();
			address.setStreetName(request.getParameter("street"));
			address.setPostalCode(request.getParameter("postalCode"));
			restaurant.setAddress(address);
			
			// Create Social Media object
			List<SocialMedia> socialMediaList = new ArrayList<>();
			SocialMedia instagram = new SocialMedia();
			instagram.setPlatform("Instagram");
			instagram.setUrl(request.getParameter("instagramUrl"));
			instagram.setRestaurant(restaurant);
			socialMediaList.add(instagram);
			
			SocialMedia facebook = new SocialMedia();
			facebook.setPlatform("Facebook");
			facebook.setUrl(request.getParameter("facebookUrl"));
			facebook.setRestaurant(restaurant);
			socialMediaList.add(facebook);
			
			restaurant.setSocialMedia(socialMediaList);
			
			// Persist Restaurant 
			restaurantDao.createRestaurant(restaurant);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().write("{\"message\": \"Restaurant created successfully!\"}");
		}
		catch (Exception e)
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\": \"Error creating restaurant.\"}");
			e.printStackTrace();
		}
	}
	
}
