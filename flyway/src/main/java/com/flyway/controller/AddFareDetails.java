package com.flyway.controller;

import java.io.IOException;

import com.flyway.Dao.AdminDAO;
import com.flyway.model.Fare;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/addfaredetails")
public class AddFareDetails extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	RequestDispatcher rd;
	boolean flag =  false;
	HttpSession session = request.getSession(false);
	String flightNumber = (String)session.getAttribute("flightnumber");
	Integer conFlightNumber = 0;
	String travelClass = request.getParameter("travelclass");
	String fare = request.getParameter("fare");
	Fare fareObj = new Fare();
	AdminDAO admin = new AdminDAO();
	String status = "";
	double classFare = 0.00;
	try {
		conFlightNumber = Integer.parseInt(flightNumber);
		classFare = Double.parseDouble(fare);

	}catch(Exception e) {
		flag = true;
	}


	if((conFlightNumber != 0 && conFlightNumber != null) && 
			(travelClass != null && travelClass.trim() != "") &&
			(classFare != 0.00) && (flag == false)) {

		fareObj.setFlightNumber(conFlightNumber);
		fareObj.setTravelClass(travelClass);
		fareObj.setFare(classFare);

		status = admin.addFare(fareObj);
		if(status == "SUCCESS") {

			request.setAttribute("SUCCESS", "Fare Added Successfully");
			rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
			rd.forward(request, response);

		}else if(status == "FAIL") {

			request.setAttribute("FAIL", "ERROR Occured while adding Fare");
			rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
			rd.forward(request, response);


		}

	}else {

		request.setAttribute("FAIL", "ERROR Occured while adding Fare");
		rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
		rd.forward(request, response);
	}

}

}