package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import org.json.JSONObject;
import com.foodwaste.model.*;
import java.util.*;


public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static List<User> users=LoginServlet.users;
	
	private boolean emailExists(String email) {
		for (User user:users) {
			if(user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String usertype=request.getParameter("usertype");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");

        

        JSONObject json = new JSONObject();
        
        if (emailExists(email)) {
        	User user=new User();
        	user.setName(name);
        	user.setEmail(email);
        	user.setPassword(password);
        	user.setUsertype(usertype);
        	user.setPhone(phone);
        	user.setAddress(address);
        	
        	users.add(user);
        }
        
        json.put("status", "success");
        json.put("message", "User registered successfully!");

        out.print(json.toString());
    }
}
