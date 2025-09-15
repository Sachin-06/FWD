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
		
		 String donorIdStr = request.getParameter("donorid");
	        String foodtype = request.getParameter("foodType");
	        String quantity = request.getParameter("quantity");
	        String location = request.getParameter("pickupLocation");
	        
	        int donorid = 0;
	        try {
	            donorid = Integer.parseInt(donorIdStr);
	        } catch (NumberFormatException e) {
	            // If invalid donorId, send error response and exit
	            JSONObject errorJson = new JSONObject();
	            errorJson.put("status", "fail");
	            errorJson.put("message", "Invalid donor ID format");
	            out.print(errorJson.toString());
	            return;
	        }
	        
	        Donation donation = new Donation();
	        donation.setId(donations.size() + 1);
	        donation.setDonorid(donorid);
	        donation.setFoodtype(foodtype);
	        donation.setQuantity(quantity);
	        donation.setPickup(location);
	        donation.setStatus("pending");

	        // Add the donation to our in-memory list
	        donations.add(donation);
	        
	     // Build the JSON response to notify the client it worked
	        JSONObject successJson = new JSONObject();
	        successJson.put("status", "success");
	        successJson.put("message", "Donation submitted successfully!");
	        successJson.put("donationId", donation.getId());
	        successJson.put("foodType", donation.getFoodtype());
	        successJson.put("quantity", donation.getQuantity());
	        successJson.put("pickupLocation", donation.getPickup());
	        successJson.put("statusText", donation.getStatus());    
		
	        out.print(successJson.toString());
	}
}

