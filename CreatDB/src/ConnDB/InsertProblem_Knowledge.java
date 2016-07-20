package ConnDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class InsertProblem_Knowledge {
	
	public static void main(String[] args) throws SQLException {
		try{
			File f = new File("D:\\knowledgepoint.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int i;
			ResultSet set = null;
			String kid = null;
			while((line = br.readLine()) != null){
				String[] sp = line.split("\t");
				String pid = sp[0];
				System.out.println(pid);
				for(i = 1; i < sp.length; i++){
					String kname = sp[i];
					ConnDB conn = new ConnDB(Constant.Username, Constant.Password);
					set = conn.SearchKidFromKname(kname);
					if (set.next()){
						kid = set.getString(1);
					}
					set.close();
					conn.Close();
					String SQL = "insert into problem_knowledge values('" + pid + "','" + kid + "')";
					conn = new ConnDB(Constant.Username, Constant.Password);
					conn.Insert(SQL);
					conn.Close();
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
