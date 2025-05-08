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
		try
		{
			restaurantDao = new RestaurantDao();
			transactionManager = new TransactionManager(restaurantDao.buildSessionFactory());
			
			System.out.println("RestaurantDao initialized and DB connection tested.");
			
			Restaurant res = new Restaurant();
			
			res.setName("Kotkot");
			res.setAddress("Hämeentie");
			res.setDescription("Delicious grilled chicken");
			res.setIsOpen(true);
			res.setPhoneNumber("00000");
			res.setOpeningHours("10:00 - 22:00");
			res.setRating(4.5);
			
			// Create Social Media entries
			SocialMedia instagram = new SocialMedia();
			instagram.setPlatform("Instagram");
			instagram.setUrl("https://instagram.com/kotkot");
			instagram.setRestaurant(res);  // Link to restaurant
			
			SocialMedia facebook = new SocialMedia();
			facebook.setPlatform("Facebook");
			facebook.setUrl("https://facebook.com/kotkot");
			facebook.setRestaurant(res);  // Link to restaurant
			
			// Add to restaurant
			List<SocialMedia> socialMediaList = new ArrayList<>();
			socialMediaList.add(instagram);
			socialMediaList.add(facebook);
			res.setSocialMedia(socialMediaList);
			
			transactionManager.beginTransaction();
			try
			{
				// Save the restaurant entity
				transactionManager.getCurrentSession().save(res);
				
				// Commit transaction
				transactionManager.commit();
				System.out.println("Restaurant saved successfully!");
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
		catch (Exception e)
		{
			throw new ServletException("Failed to initialize RestaurantDao", e);
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
}
