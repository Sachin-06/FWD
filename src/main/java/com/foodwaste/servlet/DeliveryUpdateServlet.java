package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;


import org.json.JSONObject;


import java.sql.*;
import com.foodwaste.util.DBConnection;

public class DeliveryUpdateServlet extends HttpServlet{
	
	public static final long serialVersionUID=1L;
	
	@Override
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		
		//response is in json format
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		
		JSONObject json=new JSONObject();
		
		//role check and session 
		HttpSession session = request.getSession(false);
		
		//if no session or role is missing or role is nor delivery(no access)
		if(session==null||session.getAttribute("role")==null||!"delivery".equals(session.getAttribute("role"))) {
			json.put("status", "fail");
			json.put("message", "Unauthorized access");
			out.print(json.toString());
			return;
		}
		
		//delivery person input
		int donationId=Integer.parseInt(request.getParameter("donation_id"));
		
		//new status: picked_up/delivered
		String newStatus=request.getParameter("status");
		
		Connection con=null;
		
		try {
			con=DBConnection.getConnection();
			
			con.setAutoCommit(false);
			
			//update delivery table
			String updateDeliverySql="UPDATE delivery SET status=? WHERE donation_id=?";
			
            PreparedStatement ps1 = con.prepareStatement(updateDeliverySql);
            ps1.setString(1, newStatus);  // picked_up or delivered
            ps1.setInt(2, donationId);
            ps1.executeUpdate();
            
            //update donation table
            String updateDonationSql =
                    "UPDATE donations SET delivery_status = ? WHERE id = ?";

                PreparedStatement ps2 = con.prepareStatement(updateDonationSql);
                ps2.setString(1, newStatus);  // keep both tables in sync
                ps2.setInt(2, donationId);
                ps2.executeUpdate();
                
                //if both are success save it
                con.commit();
                
                //success message
                json.put("status", "success");
                json.put("message","Delivery status updates successfully");
		}catch (Exception e) {
			// if any fails rollback
			try {
				if(con!=null) con.rollback();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			e.printStackTrace();
			json.put("status","error");
			json.put("message", "server error");
			
		}finally {
			//close DB connection always
			
			try {
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		out.print(json.toString());

		}
	}
	


