package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {
	private static String URL = "jdbc:mysql://localhost:3306/search3?characterEncoding=utf8";
	private static String problem = "problem";
	private static String knowledge = "knowledge";
	private static String report = "report";
	private static String knowledge_report = "knowledge_report";
	private static String problem_report = "problem_report";
	private PreparedStatement ps = null;
	private Connection connection = null;
	private Statement statement = null;
	private Statement statement2 = null;
	private ResultSet resultset = null;
	
	/*
	 * 该类中使用到的Sql语句再长也不能换行来写！！否则会报错！
	 * 正确：select pid from problem where pname = 'xxx'
	 * 错误：select pid from problem
	 *     where pname = 'xxx'
	 */
	
	
	public ConnDB(String username, String password){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL, username, password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//根据id查询题目信息（pid, pname, plevel）
	public ResultSet SearchProblem(String keytype, String keyword) throws SQLException{
		
		if(keyword.equals("*"))
			ps = connection.prepareStatement("select * from problem");
		else if(keytype.equals("ID") && !keyword.equals("*")){
			ps = connection.prepareStatement("select problem.pid, pname, plevel from problem where pid like ?");
			ps.setString(1, "%" + keyword + "%");
		}
		else if(keytype.equals("Title") && !keyword.equals("*")){
			ps = connection.prepareStatement("select pid, pname, plevel from problem where pname like ?");
			ps.setString(1, "%" + keyword + "%");
		}
		else if(keytype.equals("Knowledge") && !keyword.equals("*")){
			ps = connection.prepareStatement("SELECT DISTINCT(problem.pid), pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid = problem_knowledge.pid AND problem_knowledge.kid = knowledge.kid AND (knowledge.kcname like ? or knowledge.kename like ?) ");
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
		}
		try{
			if(resultset != null){
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch(Exception e){
			e.printStackTrace();
		}
		return resultset;
	}
	
		//查询符合条件的题目数量
	public int SearchProblemCount(String keytype, String keyword) throws SQLException {
		
		if(keyword.equals("*"))
			ps = connection.prepareStatement("select count(*) from problem");
		else if(keytype.equals("ID") && !keyword.equals("*")){
			ps = connection.prepareStatement("select count(pid) from problem where pid like ?");
			ps.setString(1, "%" + keyword + "%");
		}
		else if(keytype.equals("Title") && !keyword.equals("*")){
			ps = connection.prepareStatement("select count(pid) from problem where pname like ?");
			ps.setString(1, "%" + keyword + "%");
		}
		else if(keytype.equals("Knowledge") && !keyword.equals("*")){
			ps = connection.prepareStatement("SELECT count(distinct problem.pid) FROM problem, problem_knowledge, knowledge WHERE problem.pid = problem_knowledge.pid AND problem_knowledge.kid = knowledge.kid AND (knowledge.kcname like ? or knowledge.kename like ?)");
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
		}
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
			resultset.next();
			return resultset.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//根据题目id查询解题报告title,url,priority
	public ResultSet SearchReport(String id) throws SQLException{
		ps = connection.prepareStatement("select rtitle, rurl, rpriority from report,problem,problem_report where problem_report.rid = report.rid and problem_report.pid = problem.pid and problem.pid= ? ");
		ps.setString(1, id);
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SearchReportCount(String id) throws SQLException{
		ps = connection.prepareStatement("select count(report.rid) from report,problem,problem_report where problem_report.rid = report.rid and problem_report.pid = problem.pid and problem.pid= ? ");
		ps.setString(1, id);
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	//根据id查询题目所对应的知识点信息
	public ResultSet SearchKnowledge(String id) throws SQLException{
		if (id.equals("*"))
			ps = connection.prepareStatement("select * from knowledge");
		else{
			ps = connection.prepareStatement("SELECT DISTINCT(knowledge.kename) FROM knowledge, problem_knowledge, problem WHERE knowledge.kid = problem_knowledge.kid AND problem_knowledge.pid = problem.pid AND problem.pid = ?");
			ps.setString(1, id);
		}
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SearchKnowledgeCount(String id) throws SQLException{
		ps = connection.prepareStatement("SELECT COUNT(DISTINCT pid) FROM problem_knowledge WHERE kid = ?");
		ps.setString(1, id);
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	//查询每个level有多少道题
	public ResultSet SearchLevelCount(String level) throws SQLException{
		ps = connection.prepareStatement("SELECT COUNT(pid) FROM problem WHERE plevel=?");
		ps.setString(1, level);
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public void UpdateSessionCount(long count) throws SQLException {
		//修改访问人次为count
		ps = connection.prepareStatement("update other set value = ? where name = \'SessionCount\'");
		ps.setLong(1, count);
		try {
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long SessionCount() throws SQLException {
		//查询访问人次
		ps = connection.prepareStatement("select value from other where name=\'SessionCount\'");
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
			resultset.next();
			return resultset.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public ResultSet DiffSort(String sort, String level) throws SQLException{
		if(sort.equals("pratio")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pratio DESC");
			ps.setString(1, level);
		}
		if(sort.equals("pratioA")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pratio ASC");
			ps.setString(1, level);
		}
		else if(sort.equals("pac")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pac DESC");
			ps.setString(1, level);
		}
		else if(sort.equals("pacA")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pac ASC");
			ps.setString(1, level);
		}
		else if(sort.equals("pname")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pname ASC");
			ps.setString(1, level);
		}
		else if(sort.equals("pnameA")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pname DESC");
			ps.setString(1, level);
		}
		else if(sort.equals("pid")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pid ASC");
			ps.setString(1, level);
		}
		else if(sort.equals("pidA")){
			ps = connection.prepareStatement("select * from problem where plevel = ? order by pid DESC");
			ps.setString(1, level);
		}
		else if(sort.equals("knowledge")){
			ps = connection.prepareStatement("SELECT problem.pid, problem.pname, problem.plevel, problem.pac, problem.pratio FROM problem, problem_knowledge, knowledge WHERE problem.pid = problem_knowledge.pid AND problem_knowledge.kid = knowledge.kid AND problem.plevel = ? ORDER BY knowledge.kename ASC");
			ps.setString(1, level);
		}
		else if(sort.equals("knowledgeA")){
			ps = connection.prepareStatement("SELECT problem.pid, problem.pname, problem.plevel, problem.pac, problem.pratio FROM problem, problem_knowledge, knowledge WHERE problem.pid = problem_knowledge.pid AND problem_knowledge.kid = knowledge.kid AND problem.plevel = ? ORDER BY knowledge.kename DESC");
			ps.setString(1, level);
		}
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet KnowSort(String sort, String id) throws SQLException{
		if(sort.equals("plevel")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by plevel ASC");
			ps.setString(1, id);
		}
		else if(sort.equals("plevelA")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by plevel DESC");
			ps.setString(1, id);
		}
		else if(sort.equals("pname")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by pname ASC");
			ps.setString(1, id);
		}
		else if(sort.equals("pnameA")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by pname DESC");
			ps.setString(1, id);
		}
		else if(sort.equals("pid")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by pid ASC");
			ps.setString(1, id);
		}
		else if(sort.equals("pidA")){
			ps = connection.prepareStatement("SELECT problem.pid, pname, plevel FROM problem, problem_knowledge, knowledge WHERE problem.pid=problem_knowledge.pid and problem_knowledge.kid=knowledge.kid and knowledge.kid = ? group by pid order by pid DESC");
			ps.setString(1, id);
		}
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SelectRoot(String root) throws SQLException{
		ps = connection.prepareStatement("select kename from knowledge where kid = ?");
		ps.setString(1, root);
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SelectSibling(int i) throws SQLException{
		if(i < 10){
			ps = connection.prepareStatement("select kid, kename from knowledge where kid like ? and length(kid) < 6");
			ps.setString(1, i + "%");
		}
		else if(i >= 10){
			ps = connection.prepareStatement("select kid, kename from knowledge where kid like ? and length(kid) = 6");
			ps.setString(1, i + "%");
		}
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SearchKenameBykid(String id) throws SQLException{
		ps = connection.prepareStatement("select kename from knowledge where kid = ?");
		ps.setString(1, id);
		
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public ResultSet SearchBySQL(String SQL){
		try {
			if (resultset != null) {
				resultset.close();
			}
			resultset = statement.executeQuery(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	
	
	public void Close() {
		try {
			if (resultset != null) {
				resultset.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (ps != null){
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
