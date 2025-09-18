package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.foodwaste.model.Donation;
import java.util.List;

public class DonorDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static List<Donation> donations = DonorSubmissionServlet.donations;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String donorIdStr = request.getParameter("donorid");
        int donorid = 0;
        try {
            donorid = Integer.parseInt(donorIdStr);
        } catch (Exception e) {
            out.print("{\"status\":\"fail\",\"message\":\"Invalid donor ID\"}");
            return;
        }

        JSONArray arr = new JSONArray();
        for (Donation d : donations) {
            if (d.getDonorid() == donorid) {
                JSONObject obj = new JSONObject();
                obj.put("id", d.getId());
                obj.put("foodtype", d.getFoodtype());
                obj.put("quantity", d.getQuantity());
                obj.put("status", d.getStatus());
                obj.put("pickupLocation", d.getPickup());
                arr.put(obj);
            }
        }

        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("donations", arr);

        out.print(result.toString());
    }
}
