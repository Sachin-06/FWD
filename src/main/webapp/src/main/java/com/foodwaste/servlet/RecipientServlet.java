package com.foodwaste.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import com.foodwaste.model.*;
import java.util.*;
import java.io.*;


public class RecipientServlet extends HttpServlet{
	public static final long serialVersionUID=1L;
	public static List<Recipient> recipients=new ArrayList<>();
	
	
	//@Override
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		
		response.setContentType("applicartion/json");
		PrintWriter out=response.getWriter();
		
		//get input fields
		
		String name=request.getParameter("name");
		String contactperson=request.getParameter("contactperson");
		String address=request.getParameter("address");
		String phone=request.getParameter("phone");
		String recipienttype=request.getParameter("type");
		
		 // Basic validation (you can add more)
        if (name == null || contactperson == null || phone == null || address == null || recipienttype == null ||
            name.isEmpty() || contactperson.isEmpty() || phone.isEmpty() || address.isEmpty() || recipienttype.isEmpty()) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("status", "fail");
            errorJson.put("message", "All fields are required.");
            out.print(errorJson.toString());
            return;
        }
		
     // Create and store recipient
        Recipient recipient = new Recipient();
        recipient.setId(recipients.size() + 1);
        recipient.setName(name);
        recipient.setContactperson(contactperson);
        recipient.setPhone(phone);
        recipient.setAddress(address);
        recipient.setRecipienttype(recipienttype);
        recipients.add(recipient);
        
        // Respond with success JSON
        JSONObject successJson = new JSONObject();
        successJson.put("status", "success");
        successJson.put("message", "Recipient added successfully!");
        successJson.put("recipientId", recipient.getId());
        out.print(successJson.toString());
        
	}
	
   // @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONArray recipientArray = new JSONArray();
        for (Recipient r : recipients) {
            JSONObject json = new JSONObject();
            json.put("id", r.getId());
            json.put("name", r.getName());
            json.put("contactPerson", r.getContactperson());
            json.put("phone", r.getPhone());
            json.put("address", r.getAddress());
            json.put("recipientType", r.getRecipienttype());
            recipientArray.put(json);
        }
        
        JSONObject result=new JSONObject();
        result.put("status", "success");
        result.put("message", "List of all recipients");
        result.put("recipients", recipientArray);

        out.print(result.toString());
    }

}
