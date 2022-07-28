package com.flyway.controller;

import java.io.IOException;

import com.flyway.Dao.CustomerDAO;
import com.flyway.model.Fare;
import com.flyway.model.Flight;
import com.flyway.model.Reservation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/confirmpayment")
public class ConfirmPayment extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		String cardName = request.getParameter("cardname");
		String cardNo = request.getParameter("cardno");
		String date = request.getParameter("paymentdate");

		if(session != null && (cardName != null && cardName.trim() != "") && (cardNo != null && cardNo.trim() != "" )
				&& date != null) {

			session.setAttribute("cardname", cardName);
			session.setAttribute("cardno", cardNo);
			session.setAttribute("paymentdate", date);

			Flight flight = (Flight)session.getAttribute("flightobject");
			Fare fare = (Fare)session.getAttribute("fareobject");
			String travelDate = (String)session.getAttribute("traveldate");
			int passengers = (Integer)session.getAttribute("passengers");
			String day = (String)session.getAttribute("day");
			int customerId = (Integer)session.getAttribute("customerId");
			int bookingId = 0;

			Reservation reservation = new Reservation();
			CustomerDAO cust = new CustomerDAO();
			double totalFare = cust.calculateFare(flight.getFlightNumber(), 
					fare.getTravelClass() , passengers);

			reservation.setFlightNumber(flight.getFlightNumber());
			reservation.setTravelClass(fare.getTravelClass());
			reservation.setTravelDate(cust.getDate(travelDate));
			reservation.setPassengers(passengers);
			reservation.setTotalFare(totalFare);
			reservation.setCustomerId(customerId);

			bookingId = cust.addReservation(reservation);

			if(bookingId != 0) {
				session.setAttribute("bookingId", bookingId);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookingdetails.jsp");
				rd.forward(request, response);
			}
		}else {
			request.setAttribute("Error", "Error in processing payment please try again later");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/payment.jsp");
			rd.forward(request, response);
		}

	}
}