package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import com.foodwaste.util.DBConnection;

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONObject result = new JSONObject();

        // session and role check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null
                || !"admin".equals(session.getAttribute("role"))) {
            result.put("status", "fail");
            result.put("message", "Unauthorized access");
            out.print(result.toString());
            return;
        }

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            // fetch donations
            JSONArray donationsArr = new JSONArray();

            String donationSql =
                    "SELECT id, donor_id, food_item, quantity, location, delivery_status FROM donations";

            PreparedStatement ps1 = con.prepareStatement(donationSql);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs1.getInt("id"));
                obj.put("donor_id", rs1.getInt("donor_id"));
                obj.put("food_item", rs1.getString("food_item"));
                obj.put("quantity", rs1.getString("quantity"));
                obj.put("location", rs1.getString("location"));
                obj.put("delivery_status", rs1.getString("delivery_status"));
                donationsArr.put(obj);
            }

            // fetch delivery assignments
            JSONArray deliveriesArr = new JSONArray();

            String deliverySql =
                    "SELECT id, donation_id, delivery_person, status FROM delivery";

            PreparedStatement ps2 = con.prepareStatement(deliverySql);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs2.getInt("id"));
                obj.put("donation_id", rs2.getInt("donation_id"));
                obj.put("delivery_person", rs2.getString("delivery_person"));
                obj.put("status", rs2.getString("status"));
                deliveriesArr.put(obj);
            }

//            // fetch recipients  ✅ FIXED PART (column names corrected)
//            JSONArray recipientsArr = new JSONArray();
//
//            String recipientSql =
//                    "SELECT id, name, recipienttype, contactperson, phone, address FROM recipients";
//
//            PreparedStatement ps3 = con.prepareStatement(recipientSql);
//            ResultSet rs3 = ps3.executeQuery();
//
//            while (rs3.next()) {
//                JSONObject obj = new JSONObject();
//                obj.put("id", rs3.getInt("id"));
//                obj.put("name", rs3.getString("name"));
//                obj.put("recipienttype", rs3.getString("recipienttype"));
//                obj.put("contactperson", rs3.getString("contactperson"));
//                obj.put("phone", rs3.getString("phone"));
//                obj.put("address", rs3.getString("address"));
//                recipientsArr.put(obj);
//            }

            // final json response
            result.put("status", "success");
            result.put("donations", donationsArr);
            result.put("deliveries", deliveriesArr);
            //result.put("recipients", recipientsArr);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Server error");
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        out.print(result.toString());
    }
}
