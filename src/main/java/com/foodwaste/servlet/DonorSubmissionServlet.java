package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONObject;
import com.foodwaste.model.*;
import java.util.*;


public class DonorSubmissionServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	public static List<Donation>donations=new ArrayList<>();
	
	
	@Override
	 protected void doPost(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		
		response.setContentType("applicarion/json");
		PrintWriter out =response.getWriter();
		
	}
}

