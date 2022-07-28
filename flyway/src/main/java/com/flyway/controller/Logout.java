package com.flyway.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Logout")
public class Logout extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("customerId")!=null)
		{
			request.getSession().invalidate();
			
			response.sendRedirect("index.jsp");
		}else if(request.getSession().getAttribute("adminId")!=null) {
			request.getSession().invalidate();
			
			response.sendRedirect("index.jsp");
		}else {
			
			request.getSession().invalidate();
			
			response.sendRedirect("index.jsp");
			
		}	
	}

	

}
		
		
		
	