package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONObject;
import com.foodwaste.model.Donation;
import com.foodwaste.model.Delivery;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles admin assignments of a delivery person and recipient to a donation.
 */
public class DeliveryAssignmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Use the same donations list you previously stored in DonorSubmissionServlet
    private static List<Donation> donations = DonorSubmissionServlet.donations;

    // List to store all deliveries assigned so far
    private static List<Delivery> deliveries = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Tell browser to expect JSON response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Get input fields from request
        String donationIdStr = request.getParameter("donationId");
        String deliveryPersonIdStr = request.getParameter("deliveryPersonId");
        String recipientIdStr = request.getParameter("recipientId");

        // Convert input strings to integers; if malformed, send error JSON and end
        int donationId, deliveryPersonId, recipientId;
        try {
            donationId = Integer.parseInt(donationIdStr);
            deliveryPersonId = Integer.parseInt(deliveryPersonIdStr);
            recipientId = Integer.parseInt(recipientIdStr);
        } catch (NumberFormatException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("status", "fail");
            errorJson.put("message", "Invalid input: IDs must be numeric.");
            out.print(errorJson.toString());
            return;
        }

        // Find the donation in the list by ID and ensure it's pending
        Donation targetDonation = null;
        for (Donation d : donations) {
            if (d.getId() == donationId && "pending".equalsIgnoreCase(d.getStatus())) {
                targetDonation = d;
                break;
            }
        }

        if (targetDonation == null) {
            // Donation not found or already assigned
            JSONObject errorJson = new JSONObject();
            errorJson.put("status", "fail");
            errorJson.put("message", "Donation not found or already assigned.");
            out.print(errorJson.toString());
            return;
        }

        // Create new delivery assignment record
        Delivery delivery = new Delivery();
        delivery.setId(deliveries.size() + 1); // Simpler auto-increment ID
        delivery.setDonationid(donationId);
        delivery.setPersonid(deliveryPersonId);
        delivery.setRecipientid(recipientId);
        delivery.setStatus("assigned");

        // Save delivery and update donation status
        deliveries.add(delivery);
        targetDonation.setStatus("assigned");

        // Build success JSON response with details
        JSONObject successJson = new JSONObject();
        successJson.put("status", "success");
        successJson.put("message", "Delivery assigned successfully.");
        successJson.put("deliveryId", delivery.getId());
        successJson.put("donationId", donationId);
        successJson.put("deliveryPersonId", deliveryPersonId);
        successJson.put("recipientId", recipientId);
        successJson.put("status", delivery.getStatus());

        // Send response back to client
        out.print(successJson.toString());
    }
}
