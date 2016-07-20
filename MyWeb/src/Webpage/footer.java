package Webpage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.ConnDB;


public class footer extends HttpServlet{
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		out.println("<!DOCTYPE html><html><head><link rel=\"stylesheet\" href=\"css/bootstrap3.0/bootstrap.min.css\"></head>");
		out.println("<body style=\"font-weight:bold;\">");
		out.println("<p><center>Donghua University ACM-ICPC Training Team</center></p>");
		out.println("<p><center>Current Online Visitors: ");
		out.println("<strong>" + session.getServletContext().getAttribute("online") + "</strong></center></p>");
		out.println("<p><center>Total          Visitors: ");
		try {
			out.println("<strong>" + CountSession.SetVisitor(false) + "</strong></center></p>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<p><center>Copyright © 2014 Donghua University</strong></center></p>");
		out.println("</font></body></html>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
