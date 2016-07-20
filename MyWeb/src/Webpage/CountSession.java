package Webpage;

import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.ConnDB;

public class CountSession implements HttpSessionListener {
	
	public void sessionCreated(HttpSessionEvent event){
		HttpSession session = event.getSession();
		SetOnline(event, true);
		try {
			SetVisitor(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sessionDestroyed(HttpSessionEvent event) {
		SetOnline(event, false);
	}
	private synchronized void SetOnline(HttpSessionEvent event, boolean add) {
		//设置当前在线人数
		HttpSession session = event.getSession();
		ServletContext context = session.getServletContext();
		Integer online = (Integer) context.getAttribute("online");

		if (add) {
			if (online == null) {
				online = new Integer(1);
			} else if (online < 0) {
				online = new Integer(1);
			} else {
				online = new Integer(online + 1);
			}
			context.setAttribute("online", online);
		} else {
			if (online == null) {
				online = new Integer(0);
			} else if (online < 1) {
				online = new Integer(0);
			} else {
				online = new Integer(online - 1);
			}
			context.setAttribute("online", online);
		}
	}

	public static synchronized long SetVisitor(boolean add) throws SQLException {
		ConnDB conn = new ConnDB(Database.Constant.Username, Database.Constant.Password);
		long count = conn.SessionCount();
		if (add) {
			conn.UpdateSessionCount(++count);
		}
		conn.Close();
		return count;
	}
	

}
