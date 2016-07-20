package ConnDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class InsertProblem {
	
		public static void main(String[] args) throws SQLException {
			long start = System.currentTimeMillis();
			try{
				File f = new File("D:\\data.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line;
				int count = 0;
				while((line = br.readLine()) != null){
					count++;
					String[] sp = line.split("\t");
					String rid = Integer.toString(count);
					String pid = sp[0];
					String rtitle = sp[2];
					rtitle = rtitle.replaceAll("'", "\\\\'").replaceAll("¡¯", "\\\\'").replaceAll(" +", " ").replaceAll("[?]+", " ");
					System.out.println(rtitle);
					
					String rpriority = sp[3];
					String rurl = sp[4];
				
					ConnDB conn = new ConnDB(Constant.Username, Constant.Password);
					String insert_problem_report = "insert into problem_report values('" + pid + "','" + rid + "')";
					conn.Insert(insert_problem_report);
					conn.Close();
					
					conn = new ConnDB(Constant.Username, Constant.Password);
					String insert_report = "insert into report values('" + rid + "','" + rurl + "','" + rtitle + "','" + rpriority + "')";
					conn.Insert(insert_report);
					conn.Close();
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			System.out.println(end - start);
			
		}
}
