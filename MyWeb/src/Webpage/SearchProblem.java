package Webpage;

import Database.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SearchProblem extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ResultSet set = null;
		String keyword = null;
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		if(sort == null)
			sort = "pid";
		if(page == null){
			page = "1";
//			keyword = request.getParameter("SearchKeyword");
		}
		keyword = request.getParameter("SearchKeyword");
		
		String attribute = null;
		attribute = request.getParameter("SearchType");
		int PageCount = 0;
		if (keyword.equals(""))
			keyword = "*";
		ConnDB conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
		out.println("<div style=\"width:100%\" class=\"container\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Search Problem</div>");
		out.println("<div class=\"panel-body\">");
		int ProblemCount = 0;
		try {
			ProblemCount = conn.SearchProblemCount(attribute, keyword);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		conn.Close();
		if(ProblemCount == 0){
			out.println("<p><h4><center>No record matched</center></h4></p>");
			out.println("</div>");//panel-body
			out.println("</div>");//panel-heading
			out.println("</div>");//panel
		}
		else{
			out.println("<p><h4><center>The keyword you typed in is \"" + keyword + "\"</center></h4></p>");
			out.println("<p><h4><center>There are " + ProblemCount + " records matched</center></h4></p>");
			out.println("</div>");//panel-body
			
			
			out.println("<table class=\"table table-hover\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th>#</th>");
			out.println("<th>id</th>");
			out.println("<th>name</th>");
			out.println("<th>level</th>");
			out.println("<th>Reports</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			try {
				set = conn.SearchProblem(attribute, keyword);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int PageSize = Database.Constant.PerPage;
			int count = 0;
			int row = 0;
			int currPage = Integer.parseInt(page);
			try{
				set.last();
				row = set.getRow();
				if(row % PageSize == 0)
					PageCount = row/PageSize;
				else
					PageCount = row/PageSize + 1;
				set.first();
				
				do{
					count++;
					if(count <= (currPage-1)*PageSize && currPage != 1)
						continue;
					out.println("<tr>");
					out.println("<td>" + count + "</td>");
					out.println("<td>" + set.getString(1) + "</td>");
					out.println("<td>" + set.getString(2) + "</td>");
					out.println("<td>" + set.getString(3) + "</td>");
					out.println("<td><a href=\"Problem.jsp?id=" + set.getString(1) + "\">View</td>");
					out.println("</tr>");
					if(count >= currPage*PageSize)
						break;
				}while(set.next());
				set.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			conn.Close();
			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");//panel-heading
			out.println("</div>");//panel
			

			out.println("<div class=\"row\">");
			out.println("<form action=\"SearchProblem.jsp?SearchType=" + attribute + "\" method=\"POST\">");
			out.println("<div class=\"col-xs-12 text-center\">");
			out.println("<input type=\"hidden\" name=\"SearchKeyword\" value=\"" + keyword + "\" />");
			out.println("<Strong>Total Page: " + PageCount + "</strong>");
			out.println("<center><input style=\"width:80px\" name=\"page\" type=\"text\" placeholder=\"Page\" class=\"form-control\" /></center>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
			
			
			int left = currPage-1;
			int right = currPage+1;
			if(left == 0)
				left = 1;
			if(right == PageCount + 1)
				right = PageCount;

			out.println("<div class=\"row\">");
			out.println("<div class=\"col-xs-5 text-right\">");
			out.println("<form action=\"SearchProblem.jsp?page=" + left + "&SearchType=" + attribute + "\" method=\"POST\">");
			out.println("<input type=\"hidden\" name=\"SearchKeyword\" value=\"" + keyword + "\">");
			out.println("<button class=\"btn btn-primary\" name=\"submit\" type=\"submit\">&larr; Previous</button>&nbsp;&nbsp;&nbsp;");

			out.println("</form>");
			out.println("</div>");
			
			out.println("<div class=\"col-xs-2 text-center\">");
			out.println("<strong>Page&nbsp;" + currPage + "&nbsp;&nbsp;&nbsp;&nbsp;</strong>");
			out.println("</div>");

			out.println("<div class=\"col-xs-5\">");
			out.println("<form action=\"SearchProblem.jsp?page=" + right + "&SearchType=" + attribute + "\" method=\"POST\">");
			out.println("<input type=\"hidden\" name=\"SearchKeyword\" value=\"" + keyword + "\">");
			out.println("<button class=\"btn btn-primary\" name=\"submit\" type=\"submit\">Next &rarr;</button>");
			out.println("</form>");
			out.println("</div>");
			out.println("</div>");//row

			
			out.println("</br>");
		}
		
		out.println("</div>");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
