package com.rafidain.tech;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Restaurants", urlPatterns = "/*", loadOnStartup = 1)
public class RestaurantServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		
		System.out.println("Hello world");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		response.setContentType("text/plain");
		response.getWriter().println("Hello from RestaurantServlet!");
	}
}
