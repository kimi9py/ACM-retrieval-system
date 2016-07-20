package Webpage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.ConnDB;

public class DifficultyList extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String level = request.getParameter("level");
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		if(sort == null)
			sort = "pid";
		if(page == null)
			page = "1";
		int PageCount = 0;
		
		ResultSet set = null;
		ConnDB conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
		out.println("<div style=\"width:100%\" class=\"container\">");
		out.println("<div class=\"row\">");
		out.println("<div class=\"col-xs-2\">");
		out.println("<ul class=\"nav nav-pills nav-stacked\">");
		try {
			set = conn.SearchLevelCount("A");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#0066AA\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=A\">A <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("B");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#0070BB\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=B\">B <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("C");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#007ACC\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=C\">C <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("D");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#0085DD\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=D\">D <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("E");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#008FEE\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=E\">E <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("F");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#0099FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=F\">F <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("G");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#11A0FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=G\">G <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("H");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#22A7FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=H\">H <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("I");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#33ADFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=I\">I <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("J");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#44B4FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=J\">J <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("K");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#55BBFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=K\">K <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("L");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#66C2FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=L\">L <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("M");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#77C9FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=M\">M <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("N");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#88CFFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=N\">N <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("O");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#99D6FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=O\">O <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("P");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#AADDFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=P\">P <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("Q");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#BBE4FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=Q\">Q <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("R");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#CCEBFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=R\">R <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("S");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#DDF1FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=S\">S <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("T");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#EEF8FF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=T\">T <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
			set = conn.SearchLevelCount("U");
			if (set.next()){
				out.println("<li style=\"border-radius:4px;background:#FFFFFF\"><a style=\"color:#000;font-weight:bold\" href=\"DifficultyList.jsp?level=U\">U <span class=\"badge pull-right\">" + set.getString(1) + "</span></a><li>");
				set.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.Close();
		out.println("</ul>");
		out.println("</div>");//col-md-2
		
		out.println("<div class=\"col-xs-10\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">");
		
		if(level == null){
			out.println("<p><h2><Strong><center>Difficulty List</center></Strong></h2></p>");
			out.println("</div>");//panel-heading
			out.println("<div class=\"panel-body\">");
			out.println("<p><h4>The system includes 13684 ACM problems from HDU, POJ, UVA, ZOJ. " +
					"Because the fundation time is different among 4 OJ websites, the classification method can't be only based on their accepted times." +
					"Thus, according to the ratio of their AC to the average AC on their OJ, these problems are divided into 21 levels. " +
					"The levels rank from easy to hard with A, the easiest level, to U, the hardest level.</h4></p>");
			out.println("</br>");
			out.println("<p><h4>Ratio = AC / AVG(AC)  </h4></p>");
			out.println("<p><h4>AVG(AC) of HDU = 1099.1119  </h4></p>");
			out.println("<p><h4>AVG(AC) of POJ = 1641.2976  </h4></p>");
			out.println("<p><h4>AVG(AC) of UVA = 3166.8664  </h4></p>");
			out.println("<p><h4>AVG(AC) of ZOJ = 457.3633  </h4></p>");
			out.println("</br>");
			out.println("<p><h4>The classification rules as follows:  </h4></p>");
			out.println("<table class=\"table table-hover text-center\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th><center>Levels</center></th>");
			out.println("<th><center>Rules</center></th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=A\">Level A: </td>");
			out.println("<td>5 < Ratio</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=B\">Level B: </td>");
			out.println("<td>2.5 < Ratio <= 5</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=C\">Level C: </td>");
			out.println("<td>1.5 < Ratio <= 2.5</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=D\">Level D: </td>");
			out.println("<td>1 < Ratio <= 1.5</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=E\">Level E: </td>");
			out.println("<td>0.91 < Ratio <= 1</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=F\">Level F: </td>");
			out.println("<td>0.82 < Ratio <= 0.91</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=G\">Level G: </td>");
			out.println("<td>0.73 < Ratio <= 0.82</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=H\">Level H: </td>");
			out.println("<td>0.64 < Ratio <= 0.73</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=I\">Level I: </td>");
			out.println("<td>0.55 < Ratio <= 0.64</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=J\">Level J: </td>");
			out.println("<td>0.46 < Ratio <= 0.55</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=K\">Level K: </td>");
			out.println("<td>0.37 < Ratio <= 0.46</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=L\">Level L: </td>");
			out.println("<td>0.28 < Ratio <= 0.37</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=M\">Level M: </td>");
			out.println("<td>0.19 < Ratio <= 0.28</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=N\">Level N: </td>");
			out.println("<td>0.15 < Ratio <= 0.19</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=O\">Level O: </td>");
			out.println("<td>0.09 < Ratio <= 0.15</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=P\">Level P: </td>");
			out.println("<td>0.075 < Ratio <= 0.09</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=Q\">Level Q: </td>");
			out.println("<td>0.06 < Ratio <= 0.075</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=R\">Level R: </td>");
			out.println("<td>0.045 < Ratio <= 0.06</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=S\">Level S: </td>");
			out.println("<td>0.03 < Ratio <= 0.045</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=T\">Level T: </td>");
			out.println("<td>0.015 < Ratio <= 0.03</td></tr>");
			out.println("<tr><td><a href=\"DifficultyList.jsp?level=U\">Level U: </td>");
			out.println("<td>0 < Ratio <= 0.015</td></tr>");
			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");//panel-body
			out.println("</div>");//panel
				
		}
		else{
			out.println("<p><h2><Strong><center>" + level + " Level Problems</center></Strong></h2></p>");
			out.println("</div>");//panel-heading
			out.println("<div class=\"panel-body\">");
			out.println("<table class=\"table table-hover\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th>#</th>");
			if(sort.equals("pid"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on ID\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pidA\">ID</a></center></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on ID\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pid\">ID</a></center></th>");
			if(sort.equals("pname"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Name\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pnameA\">Name</a></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Name\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pname\">Name</a></th>");
			if(sort.equals("knowledge"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Knowledge\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=knowledgeA\">Knowledge</a></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Knowledge\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=knowledge\">Knowledge</a></th>");
			if(sort.equals("pac"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on AC\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pacA\">AC</a></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on AC\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pac\">AC</a></th>");
			if(sort.equals("pratio"))
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Ratio\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pratioA\">Ratio[ AC / AVG(AC) ]</a></th>");
			else
				out.println("<th><a data-toggle = \"tooltip\" title = \"sort based on Ratio\" href=\"DifficultyList.jsp?level=" + level + "&page=" + page + "&sort=pratio\">Ratio[ AC / AVG(AC) ]</a></th>");
			out.println("<th>Reports</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			
			int row = 0;
			conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			ConnDB conn2 = new ConnDB(Database.Constant.Username, Database.Constant.Password);
			
			try {
				set = conn.DiffSort(sort, level);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
			int PageSize = Database.Constant.PerPage;
			int count = 0;
			int currPage = 0;
			try{
				currPage = Integer.parseInt(page);
				
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
					out.println("<td>");
					ResultSet set_knowledge = conn2.SearchKnowledge(set.getString(1));
					while(set_knowledge.next()){
						out.println(set_knowledge.getString(1) + ";&nbsp;");
					}
					set_knowledge.close();
					out.println("</td>");
					out.println("<td>" + set.getString(4) + "</td>");
					out.println("<td>" + set.getString(5) + "</td>");
					out.println("<td><a href=\"Problem.jsp?id=" + set.getString(1) + "\">View</a></td>");
					out.println("</tr>");
					if(count >= currPage*PageSize)
						break;
				}while(set.next());
				set.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			conn.Close();
			conn2.Close();
			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");//panel-body
			out.println("</div>");//panel
			int left = currPage-1;
			int right = currPage+1;
			if(left == 0)
				left = 1;
			if(right == PageCount + 1)
				right = PageCount;
			out.println("<ul class=\"pager\">");
			out.println("<li><Strong>Total Page: " + PageCount + "</strong></li>");
			out.println("<form action=\"DifficultyList.jsp?level=" + level + "&sort=" + sort + "\" method=\"POST\">");
			out.println("</br>");
			out.println("<li><center><input style=\"width:80px\" name=\"page\" type=\"text\" placeholder=\"Page\" class=\"form-control\" /></center></li>");
			out.println("</form>");
			out.println("</br>");
			String URLleft = "DifficultyList.jsp?page=" + left + "&level=" + level + "&sort=" + sort;
			out.println("<li><a href=\"" + URLleft + "\">&larr; Previous</a></li>&nbsp;&nbsp;&nbsp;");
			out.println("<li><strong>Page&nbsp;" + currPage + "&nbsp;&nbsp;&nbsp;</strong></li>");
			String URLright = "DifficultyList.jsp?page=" + right + "&level=" + level + "&sort=" + sort;
			out.println("<li><a href=\"" + URLright + "\">Next &rarr;</a></li>");
			out.println("</ul>");
			out.println("</br>");
		}
		
		out.println("</div>");//col-md-10
		out.println("</div>");//row
		out.println("</div>");//container
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
