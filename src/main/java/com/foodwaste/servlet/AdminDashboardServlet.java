package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.foodwaste.model.Donation;
import com.foodwaste.model.Recipient;
import com.foodwaste.model.Delivery;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static List<Donation> donations = DonorSubmissionServlet.donations;
    private static List<Delivery> deliveries = DeliveryAssignmentServlet.deliveries;
    private static List<Recipient> recipients = RecipientServlet.recipients;

    @Override
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//session check
    	HttpSession session= request.getSession(false);
    	if(session==null||session.getAttribute("role")==null) {
    		response.sendRedirect("login.html");
    		return;
    	}
    	
    	//role check (admin only)
    	if(!"admin".equals(session.getAttribute("role"))) {
    		response.sendRedirect("login.html");
    		return;
    	}

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONArray donationsArr = new JSONArray();
        for (Donation d : donations) {
            JSONObject obj = new JSONObject();
            obj.put("id", d.getId());
            obj.put("foodtype", d.getFoodtype());
            obj.put("quantity", d.getQuantity());
            obj.put("status", d.getStatus());
            obj.put("pickupLocation", d.getPickup());
            obj.put("donorid", d.getDonorid());
            donationsArr.put(obj);
        }

        JSONArray deliveriesArr = new JSONArray();
        for (Delivery d : deliveries) {
            JSONObject obj = new JSONObject();
            obj.put("id", d.getId());
            obj.put("donationId", d.getDonationid());
            obj.put("deliveryPersonId", d.getPersonid());
            obj.put("recipientId", d.getRecipientid());
            obj.put("status", d.getStatus());
            deliveriesArr.put(obj);
        }

        JSONArray recipientsArr = new JSONArray();
        for (Recipient r : recipients) {
            JSONObject obj = new JSONObject();
            obj.put("id", r.getId());
            obj.put("name", r.getName());
            obj.put("recipientType", r.getRecipienttype());
            obj.put("contactPerson", r.getContactperson());
            obj.put("phone", r.getPhone());
            obj.put("address", r.getAddress());
            recipientsArr.put(obj);
        }

        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("donations", donationsArr);
        result.put("deliveries", deliveriesArr);
        result.put("recipients", recipientsArr);

        out.print(result.toString());
    }
}
