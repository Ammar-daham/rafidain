package com.rafidain.tech;

import java.io.IOException;
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
import com.rafidain.tech.persistence.Menu;
import com.rafidain.tech.persistence.MenuItem;
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
		
		transactionManager.beginTransaction();
		try
		{
			
			//						for (FinnishCity cityEnum : FinnishCity.values())
			//						{
			//							City city = new City();
			//							city.setName(cityEnum); // Set the enum value
			//							transactionManager.getCurrentSession().persist(city); // Save City entity
			//						}
			Restaurant restaurant = transactionManager.getCurrentSession().get(Restaurant.class, 252);
			
			System.out.println("res " + restaurant.toString());
			
			//transactionManager.commit();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			transactionManager.endTransaction();
		}
						

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
				case "addMenu":
					addMenu(request, response);
					break;
				case "addMenuItem":
					addMenuItem(request, response);
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
			Address address = restaurant.getAddress();
			address.setStreetName(address.getStreetName());
			address.setPostalCode(address.getPostalCode());
			restaurant.setAddress(address);
			
			// Create Social Media objects
			List<SocialMedia> socialMediaList = restaurant.getSocialMedia();
			if (socialMediaList != null)
			{
				for (SocialMedia sm : socialMediaList)
				{
					sm.setPlatform(sm.getPlatform());
					sm.setUrl(sm.getUrl());
					sm.setRestaurant(restaurant);
				}
			}
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
	
	private void addMenu(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			Menu menu = objectMapper.readValue(request.getInputStream(), Menu.class);
			
			Long restaurantId = Long.parseLong(request.getParameter("restaurantId"));
			Restaurant restaurant = transactionManager.getCurrentSession().get(Restaurant.class, restaurantId);
			menu.setName(menu.getName());
			
			List<MenuItem> menuItems = menu.getMenuItems();
			// Associate menu items with the menu and restaurant
			if (menuItems != null)
			{
				for (MenuItem item : menuItems)
				{
					item.setMenu(menu);
					item.setName(item.getName());
					item.setDescription(item.getDescription());
					item.setPrice(item.getPrice());
					item.setRestaurant(restaurant);
				}
			}
			
			menu.setMenuItems(menuItems);
			
			restaurantDao.addMenu(restaurantId, menu);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().write("{\"message\": \"Menu added successfully!\"}");
		}
		catch (Exception e)
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\": \"Error adding menu.\"}");
			e.printStackTrace();
		}
	}
	
	private void addMenuItem(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			MenuItem menuItem = objectMapper.readValue(request.getInputStream(), MenuItem.class);
			
			Long restaurantId = Long.parseLong(request.getParameter("restaurantId"));
			Long menuId = Long.parseLong(request.getParameter("menuId"));
			
			restaurantDao.addMenuItem(restaurantId, menuId, menuItem);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().write("{\"message\": \"Menu item added successfully!\"}");
		}
		catch (Exception e)
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\": \"Error adding menu item.\"}");
			e.printStackTrace();
		}
	}
	
}
