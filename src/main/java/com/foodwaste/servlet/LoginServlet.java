package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import org.json.JSONObject;
import com.foodwaste.model.*;
import java.util.*;

public class LoginServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	private static List<User> users=new ArrayList<>();
	public static void addUser(User user) {
		users.add(user);
	}
	
	
	
	private User authenticate(String email,String password) {
		
		for(User user:users) {
			
			if (user.getEmail().equalsIgnoreCase(email)&&user.getPassword().equals(password)) {
				
				return user;
			}
		}
		return null;
	}
	
	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        JSONObject json = new JSONObject();
        
        User user = authenticate(email,password);
        if (user !=null) {
            json.put("status", "success");
            json.put("message", "Login successful!");
        } else {
            json.put("status", "fail");
            json.put("message", "Invalid credentials");
        }

        out.print(json.toString());
    }
}
