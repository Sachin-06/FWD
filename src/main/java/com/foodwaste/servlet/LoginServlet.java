package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import org.json.JSONObject;

public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        JSONObject json = new JSONObject();
        if (email.equals("sachin") && password.equals("pass123")) {
            json.put("status", "success");
            json.put("message", "Login successful!");
        } else {
            json.put("status", "fail");
            json.put("message", "Invalid credentials");
        }

        out.print(json.toString());
    }
}
