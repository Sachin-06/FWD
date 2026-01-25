package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.json.JSONObject;
import java.sql.*;

import com.foodwaste.util.DBConnection;

public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Check email from DATABASE
    private boolean emailExists(Connection con, String email) throws Exception {
        String sql = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype"); // donor/admin/delivery

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            if (emailExists(con, email)) {
                json.put("status", "fail");
                json.put("message", "Email already exists");
            } else {
                String sql =
                    "INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, usertype);

                ps.executeUpdate();

                json.put("status", "success");
                json.put("message", "User registered successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Server error");
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        out.print(json.toString());
    }
}
