package com.flyway.controller;

import java.io.IOException;

import com.flyway.Dao.CustomerDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/updatepassword")
public class UpdatePassword extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		HttpSession session = request.getSession(false);	
		Integer customerId = (Integer)session.getAttribute("customerId");
		CustomerDAO cust = new CustomerDAO();
		String status = "";

		if(customerId != null) {

			String password = request.getParameter("password");

			if(password != null && password.trim() != "") {

				status = cust.updatePassword(customerId, password);

				if(status == "SUCCESS") {
					request.setAttribute("SUCCESS", "Password Successfully Updated");
					rd = getServletContext().getRequestDispatcher("/customerdetails.jsp");
					rd.forward(request, response);
				}else if(status == "FAIL") {

					request.setAttribute("FAIL", "Error while Updating Password");
					rd = getServletContext().getRequestDispatcher("/customerdetails.jsp");
					rd.forward(request, response);
				}
			}else {

				request.setAttribute("FAIL", "Error while Updating Password");
				rd = getServletContext().getRequestDispatcher("/customerdetails.jsp");
				rd.forward(request, response);

			}
		}


	}

}