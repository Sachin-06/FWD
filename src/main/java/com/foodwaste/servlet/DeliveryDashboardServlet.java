//package com.foodwaste.servlet;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import java.io.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import com.foodwaste.model.Delivery;
//import java.util.List;
//
//public class DeliveryDashboardServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private static List<Delivery> deliveries = DeliveryAssignmentServlet.deliveries;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//          throws ServletException, IOException {
//    	
//    	//session check
//    	HttpSession session= request.getSession(false);
//    	if(session==null||session.getAttribute("role")==null) {
//    		response.sendRedirect("login.html");
//    		return;
//    	}
//    	
//    	//role check (delivery only)
//    	if(!"delivery".equals(session.getAttribute("role"))) {
//    		response.sendRedirect("login.html");
//    		return;
//    	}
//
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//
//        String deliveryPersonIdStr = request.getParameter("deliveryPersonId");
//        int deliveryPersonId = 0;
//        try {
//            deliveryPersonId = Integer.parseInt(deliveryPersonIdStr);
//        } catch (Exception e) {
//            out.print("{\"status\":\"fail\",\"message\":\"Invalid delivery person ID\"}");
//            return;
//        }
//
//        JSONArray arr = new JSONArray();
//        for (Delivery d : deliveries) {
//            if (d.getPersonid() == deliveryPersonId) {
//                JSONObject obj = new JSONObject();
//                obj.put("id", d.getId());
//                obj.put("donationId", d.getDonationid());
//                obj.put("recipientId", d.getRecipientid());
//                obj.put("status", d.getStatus());
//                obj.put("confirmationType", d.getConfirmationtype());
//                obj.put("confirmationData", d.getConfirmationdata());
//                arr.put(obj);
//            }
//        }
//
//        JSONObject result = new JSONObject();
//        result.put("status", "success");
//        result.put("deliveries", arr);
//
//        out.print(result.toString());
//    }
//}
