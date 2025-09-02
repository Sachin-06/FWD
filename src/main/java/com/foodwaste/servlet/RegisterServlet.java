package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import org.json.JSONObject;

public class RegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        request.getParameter("name");
        request.getParameter("email");
        request.getParameter("password");

        // Normally you'd save this to DB here

        JSONObject json = new JSONObject();
        json.put("status", "success");
        json.put("message", "User registered successfully!");

        out.print(json.toString());
    }
}
