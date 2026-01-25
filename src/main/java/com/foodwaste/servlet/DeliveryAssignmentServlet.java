package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

import org.json.JSONObject;
import com.foodwaste.util.DBConnection;


public class DeliveryAssignmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("application/json");
    	PrintWriter out=response.getWriter();
    	JSONObject json=new JSONObject();
    	
    	//session and role check(admin only)
    	HttpSession session=request.getSession(false);
    	if(session==null||session.getAttribute("role")==null||!"admin".equals(session.getAttribute("role"))) {
    		json.put("status", "fail");
    		json.put("message","Unauthorized access");
    		out.print(json.toString());
    		return;
    	}
    	
    	//admin input
    	int donationId=Integer.parseInt(request.getParameter("donation_id"));
    	String deliveryPerson=request.getParameter("delivery_person");
    	
    	Connection con=null;
    	
    	try {
    		con=DBConnection.getConnection();
    		con.setAutoCommit(false);//transaction
    		
    		//insert into delivery table
    		String insertDelivery="INSERT INTO delivery(donation_id,delivery_person,status)VALUES(?,?,?)";
    		PreparedStatement ps1=con.prepareStatement(insertDelivery);
    		ps1.setInt(1,donationId);
    		ps1.setString(2,deliveryPerson);
    		ps1.setString(3,"assigned");
    		ps1.executeUpdate();
    		
    		//donation status update
    		String updateDonation= "UPDATE donations SET delivery_status=? WHERE id=?";
    		PreparedStatement ps2=con.prepareStatement(updateDonation);
    		ps2.setString(1, "assigned");
    		ps2.setInt(2,donationId);
    		ps2.executeUpdate();
    		con.commit();
    		
    		json.put("status", "success");
    		json.put("message", "Delivery assigned successfully");
    	}catch (Exception e) {
    		try {
    			if(con!=null) con.rollback();
    		}catch(Exception ex) {
    			ex.printStackTrace();
    		}
    		e.printStackTrace();
    		json.put("status","error");
    		json.put("message", "server error");
    	}finally {
    		try {
    			if(con!=null) con.close();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    	out.print(json.toString());
    }
}
