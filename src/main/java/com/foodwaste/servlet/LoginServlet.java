package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

import org.json.JSONObject;
import com.foodwaste.util.DBConnection;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT id, type FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String role = rs.getString("type");

                // create session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("email", email);
                session.setAttribute("role", role);

                json.put("status", "success");
                json.put("role", role);
                json.put("message", "Login successful");

            } else {
                json.put("status", "fail");
                json.put("message", "Invalid email or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Server error");
        }

        out.print(json.toString());
    }
}
