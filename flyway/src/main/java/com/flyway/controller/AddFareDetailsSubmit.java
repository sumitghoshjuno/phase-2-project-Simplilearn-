package com.flyway.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/addfaredetailssubmit")
public class AddFareDetailsSubmit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		HttpSession session = request.getSession(false);
		String flightNumber = request.getParameter("flightnumber");
		if(flightNumber == null) {
			request.setAttribute("ERROR", "Cannot add Fare details");
			rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
			rd.forward(request, response);
		}else if(flightNumber != null) {
			session.setAttribute("flightnumber", flightNumber);
			rd = getServletContext().getRequestDispatcher("/addfaredetails.jsp");
			rd.forward(request, response);

		}

	}

}
		
	
