package com.flyway.controller;

import java.io.IOException;

import com.flyway.Dao.AdminDAO;
import com.flyway.model.Flight;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/addflightadmin")
public class AdminAddFlight extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		String airline = request.getParameter("airline");
		String[] days = request.getParameterValues("weekdays");
		String weekdays = String.join("_", days);
		String source = request.getParameter("source");
		String destination = request.getParameter("destination");
		Flight flight = new Flight();
		int flightNumber = 0;
		AdminDAO admin = new AdminDAO();
		HttpSession session = request.getSession(false);
		if((airline != null && airline != "") && (weekdays != null && weekdays != "") &&
				(source != null && source != "") && (destination != null && destination != "")) {

			flight.setAirline(airline);
			flight.setWeekdays(weekdays);
			flight.setSource(source);
			flight.setDestination(destination);
			flightNumber = admin.addFlight(flight); 

			if(flightNumber != 0) {
				request.setAttribute("SUCCESS", "Flight successfully added");
				rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
				rd.forward(request, response);
			}else {
				request.setAttribute("Error", "Error Occured while adding flight");
				rd = getServletContext().getRequestDispatcher("/adminaddflight.jsp");
				rd.forward(request, response);
			}


		}else {
			request.setAttribute("Error2", "Error Occured while adding flight");
			rd = getServletContext().getRequestDispatcher("/adminaddflight.jsp");
			rd.forward(request, response);
		}


	}

}