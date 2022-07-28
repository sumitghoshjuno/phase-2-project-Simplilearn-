package com.flyway.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flyway.Dao.CustomerDAO;
import com.flyway.model.Fare;
import com.flyway.model.Flight;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/showfaredetails")
public class ShowFareDetails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomerDAO cust = new CustomerDAO();
		String flightNumberStr;
		int flightNumber = 0;
		flightNumberStr = request.getParameter("flightnumber");
		if(flightNumberStr != null && flightNumberStr != "") {
			flightNumber = Integer.parseInt(flightNumberStr);
		}
		List<Fare> fareList = new ArrayList<Fare>();
		Flight flight = new Flight();
		HttpSession session = request.getSession(false);
		if(flightNumber != 0) {
			flight = cust.getFlight(flightNumber);
			fareList = cust.showFareList(flight.getFlightNumber());
			request.setAttribute("farelist", fareList);
			session.setAttribute("flightobject", flight);

			if(fareList.size() == 0 || fareList == null) {

				request.setAttribute("FAIL", "There are no fare list available. Cannot Book Tickets for " + flightNumber);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/flightdetails.jsp");
				rd.forward(request, response);

			}else {

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/faredetails.jsp");
				rd.forward(request, response);
			}


		}else {

			request.setAttribute("FAIL1", "Error Occurred while fetching fares");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/flightdetails.jsp");
			rd.forward(request, response);

		}



	}

}
