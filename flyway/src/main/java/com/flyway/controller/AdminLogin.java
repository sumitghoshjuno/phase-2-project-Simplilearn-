package com.flyway.controller;

import java.io.IOException;

import com.flyway.Dao.AdminDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/adminlogin")
public class AdminLogin extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("emailaddress");
		String password = request.getParameter("password");
		
		RequestDispatcher rd;
		
		AdminDAO admin = new AdminDAO();
		
		if((email != null && email.trim() != "") && 
				(password != null && password.trim() != "")) {
			
			
			int adminId = admin.adminLogin(email, password);
			
			if(adminId != 0) {
				
				HttpSession session = request.getSession();
				session.setAttribute("adminId", adminId);
				rd = getServletContext().getRequestDispatcher("/admindetails.jsp");
	            rd.forward(request, response);	
			}else {
				
				request.setAttribute("loginerr", "Incorrect email or Password");
				rd = getServletContext().getRequestDispatcher("/adminlogin.jsp");
	            rd.forward(request, response);
			}
			
		}else {
			
			request.setAttribute("loginerr1", "Error Occurred while Loging in");
			rd = getServletContext().getRequestDispatcher("/adminlogin.jsp");
            rd.forward(request, response);
		}
		
		
	}

}