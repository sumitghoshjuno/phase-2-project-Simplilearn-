package com.flyway.controller;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.flyway.Dao.CustomerDAO;
import com.flyway.model.Airport;
import com.flyway.model.Flight;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/bookflight")
public class ShowFlight extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomerDAO cust = new CustomerDAO();

		Airport srcAirport  = null;
		Airport destAirport = null;
		String day = "";
		String source = request.getParameter("source");
		String destination = request.getParameter("destination");
		String date = request.getParameter("traveldate");
		Date travelDate = cust.getDate(date);
		int passengers = Integer.parseInt(request.getParameter("passengers"));
		List<Flight> flightList = new ArrayList<Flight>();
		if(source != "" && destination != "" && travelDate != null
				&& passengers != 0 ){
			HttpSession session = request.getSession();
			srcAirport = cust.getAirportObject(source);
			destAirport = cust.getAirportObject(destination);
			day = cust.getDay(travelDate);
			flightList = cust.flightList(source, destination, travelDate);
			request.setAttribute("sourceairport", srcAirport);
			request.setAttribute("destairport", destAirport);
			request.setAttribute("flightlist", flightList);
			session.setAttribute("traveldate", date);
			session.setAttribute("passengers", passengers);
			session.setAttribute("day", day);



			if(flightList.size() == 0 || flightList == null) {
				request.setAttribute("FAIL", "There are no flights flying from " + srcAirport.getAirport() + " to " + destAirport.getAirport() + " on date " + date + " ." );
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookflight.jsp");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/flightdetails.jsp");
				rd.forward(request, response);
			}


		}else {
			request.setAttribute("FAIL1", "Error Occurred while searching flights.");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookflight.jsp");
			rd.forward(request, response);


		}

	}

}
	
	
