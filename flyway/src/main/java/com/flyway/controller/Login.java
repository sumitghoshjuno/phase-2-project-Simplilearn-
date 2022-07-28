package com.flyway.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.flyway.Dao.CustomerDAO;
import com.flyway.model.Flight;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/login")
public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unlikely-arg-type", "rawtypes" })
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("emailaddress");
		String password = request.getParameter("password");

		RequestDispatcher rd;
		CustomerDAO cust = new CustomerDAO();
		if((email != null && email.trim() != "") && (password != null && password.trim() != "")) {
			int customerId = cust.customerLogin(email, password);
			if(customerId != 0) {

				HashMap<String, Object> map = new HashMap<String, Object>();
				HttpSession oldSession = request.getSession(false);
				HttpSession newSession = null;
				if (oldSession != null) {
					Enumeration keys = oldSession.getAttributeNames();
					while(keys.hasMoreElements()) {
						String key = (String)keys.nextElement();
						map.put(key, oldSession.getAttribute(key));
						oldSession.removeAttribute(key);			
					}

					oldSession.invalidate();	
					newSession = request.getSession();
					for(Map.Entry<String , Object> m : map.entrySet()) {

						newSession.setAttribute((String)m.getKey(), m.getValue());
						map.remove(m);
					}


				}else if(oldSession == null)  {

					newSession = request.getSession();
				}


				Flight flight = (Flight)newSession.getAttribute("flightobject");
				if (flight == null) {

					newSession.setAttribute("customerId", customerId);
					rd = getServletContext().getRequestDispatcher("/customerdetails.jsp");
					rd.forward(request, response);

				}else {
					newSession.setAttribute("customerId", customerId);
					rd = getServletContext().getRequestDispatcher("/confirmbooking.jsp");
					rd.forward(request, response);

				}



			}else {
				request.setAttribute("loginerr", "Incorrect email or Password");
				rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(request, response);

			}
		}else {

			request.setAttribute("loginerr1", "Error Occured while login in");
			rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);

		}




	}

}

	
	
