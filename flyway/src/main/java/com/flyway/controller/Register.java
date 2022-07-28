package com.flyway.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.flyway.Dao.CustomerDAO;
import com.flyway.model.Customer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/register")
public class Register extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings({ "rawtypes", "unlikely-arg-type" })
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("emailaddress");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		int customerId = 0;
		CustomerDAO cust = new CustomerDAO();
		Customer customer = new Customer();		

		if(firstName.trim() != "" && lastName.trim() != "" && email.trim() != ""  
				&& password.trim() != "" && phone.trim() != ""  ) {
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setEmail(email);
			customer.setPassword(password);
			customer.setPhone(phone);
			customerId = cust.addCustomer(customer);
			System.out.println(customerId);

			if(customerId != 0) {
				newSession.setAttribute("customerId", customerId);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/confirmbooking.jsp");
				rd.forward(request, response);
			}else {
				request.setAttribute("Error", "Error Occured while adding customer");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
				rd.forward(request, response);

			}


		}else {
			request.setAttribute("Error1", "Error Occured while adding customer");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
			rd.forward(request, response);

		}





	}

}
		
		
	
