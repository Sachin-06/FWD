package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.json.JSONObject;
import java.sql.*;

import com.foodwaste.util.DBConnection;

public class DonorSubmissionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        //session check (donor only)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null ||
            !"donor".equals(session.getAttribute("role"))) {

            json.put("status", "fail");
            json.put("message", "Unauthorized access");
            out.print(json.toString());
            return;
        }

        //correct session attribute
        Integer donorIdObj = (Integer) session.getAttribute("userId");
        if (donorIdObj == null) {
            json.put("status", "fail");
            json.put("message", "Session expired. Please login again.");
            out.print(json.toString());
            return;
        }

        int donorId = donorIdObj;

        //donation details
        String foodItem = request.getParameter("food_item");
        String quantity = request.getParameter("quantity");
        String location = request.getParameter("location");

        try (Connection con = DBConnection.getConnection()) {

            String sql =
                "INSERT INTO donations " +
                "(donor_id, food_item, quantity, location, delivery_status) " +
                "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, donorId);
            ps.setString(2, foodItem);
            ps.setString(3, quantity);
            ps.setString(4, location);
            ps.setString(5, "pending");
            ps.executeUpdate();

            json.put("status", "success");
            json.put("message", "Donation submitted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Server error");
        }

        out.print(json.toString());
    }
}
