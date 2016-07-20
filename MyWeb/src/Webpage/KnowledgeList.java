package Webpage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import Database.ConnDB;

public class KnowledgeList extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int PageCount = 0;
		int kcount = 0;
		ResultSet set = null;
		ConnDB conn;
		
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		if(sort == null)
			sort = "pid";
		if(page == null)
			page = "1";
		out.println("<div style=\"width:100%\" class=\"container\">");
		out.println("<div class=\"row\">");
		out.println("<div class=\"col-xs-4\">");
		out.println("<div class=\"panel-group\" id=\"accordion\">");

		for (int i = 1;i <=14 ; i++){
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			String root = i + "0000";
			try {
				set = conn.SelectRoot(root);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println("<div class=\"panel panel-default\">");
			out.println("<div class=\"panel-heading\">");
			out.println("<h4 class=\"panel-title\">");
			out.println("<a style=\"font-weight:bold\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#" + i + "\">");
			try {
				if(set.next()){
					out.println(set.getString(1));
				}
				set.close();
				conn.Close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("</a>");
			out.println("</h4>");
			out.println("</div>");//panel-heading
			
			
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			ResultSet set1 = null;
			try {
				set1 = conn.SelectSibling(i);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println("<div id=\"" + i + "\" class=\"panel-collapse collapse\">");
			out.println("<div class=\"panel-body\">");
			out.println("<ul class=\"nav nav-tabs nav-stacked\">");
			try {
				while(set1.next()){
					int kid = Integer.parseInt(set1.getString(1));
					String kename = set1.getString(2);
					ConnDB conn2 = new ConnDB(Database.Constant.Username, Database.Constant.Password);
					ResultSet set2 = conn2.SearchKnowledgeCount(Integer.toString(kid));
					set2.next();
					kcount = set2.getInt(1);
					set2.close();
					conn2.Close();
					if((kid % 10000) % 100 == 0){
						out.println("<li><a href=\"KnowledgeList.jsp?id=" + kid + "\"><strong>" + kename + "<span class=\"badge pull-right\">" + kcount + "</span></strong></a></li>");
					}
					else{
						out.println("<ul>");
						out.println("<li class=\"dropdown-submenu\"><a href=\"KnowledgeList.jsp?id=" + kid + "\"><strong>" + kename + "<span class=\"badge pull-right\">" + kcount + "</span></strong></a></li>");
						out.println("</ul>");
					}
				}
				set1.close();
				conn.Close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("</ul>");
			out.println("</div>");//panel-body
			out.println("</div>");
			out.println("</div>");//panel-default
		}//for
		out.println("</div>");//panel-group
		out.println("</div>");//col-md-3
		
		out.println("<div class=\"col-xs-8\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">");
		if (id == null){
			out.println("<p><h2><strong><center>Knowledge List</center></strong></h2></p>");
			out.println("</div>");//panel-heading
			out.println("<div class=\"panel-body\">");
			out.println("<p><h4>The system classified all problems according to the knowledge points they included.</h4></p>");
			out.println("<p><h4>Based on your own needs, you can choose the knowledge points on the left to have your own practice.</h4></p>");
			for(int i = 0; i < 20; i++)
				out.println("</br>");
			out.println("</div>");//panel-body
			out.println("</div>");//panel
			out.println("</div>");//col-md-9
		}
		else{
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			try {
				set = conn.SearchKenameBykid(id);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			try {
				if(set.next()){
					out.println("<p><h2><strong><center>" + set.getString(1) + "</center></strong></h2></p>");
					out.println("</div>");//panel-heading
				}
				set.close();
				conn.Close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("<div class=\"panel-body\">");
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			try {
				set = conn.SearchKnowledgeCount(id);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				if(set.next()){
					out.println("<p><center><strong>There are " + set.getInt(1) + " records matched</strong></center></p>");
				}
				set.close();
				conn.Close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println("</div>");//panel-body
			
			out.println("<table class=\"table table-hover\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th>#</th>");
			if(sort.equals("pid"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on ID\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=pidA\">ID</center></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on ID\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=pid\">ID</center></th>");
			if(sort.equals("pname"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Name\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=pnameA\">Name</th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Name\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=pname\">Name</th>");
			if(sort.equals("plevel"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Level\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=plevelA\">Level</th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Level\" href=\"KnowledgeList.jsp?id=" + id + "&page=" + page + "&sort=plevel\">Level</th>");
			out.println("<th>Reports</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			try {
				set = conn.KnowSort(sort, id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int count = 0;
			int row = 0;
			int currPage = 0;
			int PageSize = Database.Constant.PerPage;
			try {
				currPage = Integer.parseInt(page);
				set.last();
				row = set.getRow();
				if(row % PageSize == 0)
					PageCount = row/PageSize;
				else
					PageCount = row/PageSize + 1;
				if(row != 0){
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
						out.println("<td><a href=\"Problem.jsp?id=" + set.getString(1) + "\">View</a></td>");
						out.println("</tr>");
						if(count >= currPage*PageSize)
							break;
					}while(set.next());
				}
				set.close();
				conn.Close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");//panel
			int left = currPage-1;
			int right = currPage+1;
			if(left == 0)
				left = 1;
			if(right == PageCount + 1)
				right = PageCount;
			out.println("<ul class=\"pager\">");
			out.println("<li><Strong>Total Page: " + PageCount + "</strong></li>");
			out.println("<form action=\"KnowledgeList.jsp?id=" + id + "&sort=" + sort + "\" method=\"POST\">");
			out.println("</br>");
			out.println("<li><center><input style=\"width:80px\" name=\"page\" type=\"text\" placeholder=\"Page\" class=\"form-control\" /></center></li>");
			out.println("</form>");
			out.println("</br>");
			String URLleft = "KnowledgeList.jsp?page=" + left + "&id=" + id + "&sort=" + sort;
			out.println("<li><a href=\"" + URLleft + "\">&larr; Previous</a></li>&nbsp;&nbsp;&nbsp;");
			out.println("<li><strong>Page&nbsp;" + currPage + "&nbsp;&nbsp;&nbsp;</strong></li>");
			String URLright = "KnowledgeList.jsp?page=" + right + "&id=" + id + "&sort=" + sort;
			out.println("<li><a href=\"" + URLright + "\">Next &rarr;</a></li>");
			out.println("</br>");
			out.println("</ul>");
			out.println("</div>");//col-md-9
			
		}
		
		out.println("</div>");//row
		out.println("</br>");
		out.println("</br>");
		out.println("</br>");
		out.println("</div>");//containter
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
