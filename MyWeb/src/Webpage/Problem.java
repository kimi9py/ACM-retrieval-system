package Webpage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.ConnDB;


public class Problem extends HttpServlet{
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");

		String description = null;
		int ReportCount = 0;
		if(id.contains("hdu"))
			description = "http://acm.hdu.edu.cn/showproblem.php?pid=" + id.substring(3);
		else if(id.contains("poj"))
			description = "http://poj.org/problem?id=" + id.substring(3);
		else if(id.contains("uva")){
			int num1 = Integer.parseInt(id.substring(3));
			int num2 = Integer.parseInt(id.substring(3)) / 100;
			description = "http://uva.onlinejudge.org/external/" + num2 + "/" + num1 + ".html";
		}
		else if(id.contains("zoj"))
			description = "http://acm.zju.edu.cn/onlinejudge/showProblem.do?problemCode=" + id.substring(3);
		
		ConnDB conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
		ResultSet set = null;
		try {
			set = conn.SearchProblem("ID", id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		out.println("<div style=\"width:100%\" class=\"container\">");
		out.println("<div class=\"panel panel-default\">");
		try{
			if(set.next()){
				out.println("<div class=\"panel-heading\"><h4><B>" + set.getString(1) + "&nbsp;&nbsp;&nbsp;&nbsp;" + set.getString(2) + "</B></h4></div>");
				out.println("<div class=\"panel-body\">");
				out.println("<a href=\"" + description + "\" target=\"_blank\"><B>Problem Description</B></a>");
				out.println("<p><B><h5>Level: " + set.getString(3) + "</h5></B></p>");
			}
			set.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		try {
			set = conn.SearchKnowledge(id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			out.println("<p><B><h5>Knowledge: ");
			while(set.next()){
				out.println(set.getString(1) + ";&nbsp;&nbsp;");
			}
			set.close();
			out.println("</h5></B></p>");
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		try {
			set = conn.SearchReportCount(id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			out.println("<p><B><h5>There are: ");
			while(set.next()){
				ReportCount = set.getInt(1);
				out.println(ReportCount + " records matched");
			}
			set.close();
			out.println("</h5></B></p>");
		}catch(SQLException e){
			e.printStackTrace();
		}
		out.println("</div>");//panel-body
		
		if(ReportCount == 0){
			out.println("<p><B><h5>We recommend you to find reports in: </p>");
			String baidu = "http://www.baidu.com/s?wd=" + id;
			String google  = "http://www.google.com.hk/search?q=" + id;
			out.println("<a href=\"" + baidu + "\" target=\"_blank\"><strong> Baidu : " + baidu + "</strong></a>");
			out.println("</br></br>");
			out.println("<a href=\"" + google + "\" target=\"_blank\"><strong> Google : " + google + " </strong></a>");
			out.println("</h5></B></p>");
		}
		
		out.println("<table class=\"table table-hover\">");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th>#</th>");
		out.println("<th>title</th>");
		out.println("</tr>");
		out.println("</thead>");
		
		out.println("<tbody>");
		try {
			set = conn.SearchReport(id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count = 0;
		try{
			while(set.next()){
				if(set.getString(3).equals("overhigh")){
					count++;
					out.println("<tr>");
					out.println("<td>" + count + "</td>");
					out.println("<td><a href=\"" + set.getString(2) + "\" target=\"_blank\">" + set.getString(1) + "</a></td>");
					out.println("</tr>");
				}
			}
			set.first();
			do{
				if(set.getString(3).equals("high")){
					count++;
					out.println("<tr>");
					out.println("<td>" + count + "</td>");
					out.println("<td><a href=\"" + set.getString(2) + "\" target=\"_blank\">" + set.getString(1) + "</a></td>");
					out.println("</tr>");
				}
			}while(set.next());
			set.first();
			do{
				if(set.getString(3).equals("low")){
					count++;
					out.println("<tr>");
					out.println("<td>" + count + "</td>");
					out.println("<td><a href=\"" + set.getString(2) + "\" target=\"_blank\">" + set.getString(1) + "</a></td>");
					out.println("</tr>");
				}
			}while(set.next());
			set.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		conn.Close();
		out.println("</tbody>");
		out.println("</table>");
		out.println("</div>");//panel-heading
		out.println("</div>");//panel
		out.println("</div></body></html>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
