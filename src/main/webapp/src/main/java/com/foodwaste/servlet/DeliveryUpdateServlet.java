package com.foodwaste.servlet;

import java.util.*;
import javax.servlet.*;
import com.foodwaste.model.*;
import java.io.*;
import javax.servlet.http.*;
import org.json.JSONObject;

public class DeliveryUpdateServlet extends HttpServlet{
	
	public static final long serialVersionUID=1L;
	
	private static List<Delivery>deliveries=DeliveryAssignmentServlet.deliveries;
	private static List<Donation>donations=DonorSubmissionServlet.donations;
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		
		String deliveryIdStr=request.getParameter("deliveryid");
		String status=request.getParameter("status");
		String confirmationtype=request.getParameter("confirmationtype");
		String confirmationdata=request.getParameter("confirmationdata");
		
		
		int deliveryid;
		
		try {
			deliveryid=Integer.parseInt(deliveryIdStr);
		}catch(NumberFormatException e) {
			 JSONObject errorJson = new JSONObject();
	            errorJson.put("status", "fail");
	            errorJson.put("message", "Invalid delivery ID");
	            out.print(errorJson.toString());
	            return;
	        }
		
		//find delivery record by id
		 Delivery delivery = null;
	        for (Delivery d : deliveries) {
	            if (d.getId() == deliveryid) {
	                delivery = d;
	                break;
	            }
	        }
	        
	        
	        
	        if (delivery == null) {
	            JSONObject errorJson = new JSONObject();
	            errorJson.put("status", "fail");
	            errorJson.put("message", "Delivery not found");
	            out.print(errorJson.toString());
	            return;
	        }

	        // Update delivery status and confirmation info

	        delivery.setStatus(status);
	        delivery.setConfirmationtype(confirmationtype);
	        delivery.setConfirmationdata(confirmationdata);

	        // Update linked donation status accordingly
	        for (Donation don : donations) {
	            if (don.getId() == delivery.getDonationid()) {
	                if ("picked_up".equalsIgnoreCase(status)) {
	                    don.setStatus("in_transit");
	                } else if ("delivered".equalsIgnoreCase(status)) {
	                    don.setStatus("delivered");
	                }
	                break;
	            }
	        }
	        
	        
	        
	        // Prepare success JSON response
	        JSONObject successJson = new JSONObject();
	        successJson.put("status", "success");
	        successJson.put("message", "Delivery updated successfully");
	        successJson.put("deliveryId", delivery.getId());
	        successJson.put("newStatus", delivery.getStatus());

	        out.print(successJson.toString());
		}
	}
	


