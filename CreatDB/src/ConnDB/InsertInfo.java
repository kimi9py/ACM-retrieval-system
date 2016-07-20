package ConnDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertInfo {
	public static void main(String[] args) throws SQLException {
		
		try{
			File f = new File("D:\\hduid.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			ResultSet set = null;
			while((line = br.readLine()) != null){
				String[] sp = line.split("\t");
				String pid = sp[1];
				pid = "hdu" + pid;
				String pname = sp[2];
				pname = pname.replaceAll("'", "\\\\'").replaceAll("¡¯", "\\\\'").replaceAll(" +", " ");
				System.out.println(pname);
				int pac = Integer.parseInt(sp[3]);
				
				String plevel = null;
				
				double pratio = (double) pac / 1099.1119;
				
				if (5 < pratio)
					plevel = "A";
				else if(2.5 < pratio && pratio <= 5)
					plevel = "B";
				else if(1.5<pratio && pratio <2.5)
					plevel = "C";
				else if(1<pratio && pratio<=1.5)
					plevel = "D";
				else if(0.91<pratio && pratio<=1)
					plevel = "E";
				else if(0.82<pratio && pratio<=0.91)
					plevel = "F";
				else if(0.73<pratio && pratio<=0.82)
					plevel = "G";
				else if(0.64<pratio && pratio<=0.73)
					plevel = "H";
				else if(0.55<pratio && pratio<=0.64)
					plevel = "I";
				else if(0.46<pratio && pratio<=0.55)
					plevel = "J";
				else if(0.37<pratio && pratio<=0.46)
					plevel = "K";
				else if(0.28<pratio && pratio<=0.37)
					plevel = "L";
				else if(0.19<pratio && pratio<=0.28)
					plevel = "M";
				else if(0.15<pratio && pratio<=0.19)
					plevel = "N";
				else if(0.09<pratio && pratio<=0.15)
					plevel = "O";
				else if(0.075<pratio && pratio<=0.09)
					plevel= "P";
				else if(0.06<pratio && pratio<=0.075)
					plevel = "Q";
				else if(0.045<pratio && pratio<=0.06)
					plevel = "R";
				else if(0.03<pratio && pratio<=0.045)
					plevel = "S";
				else if(0.015<pratio && pratio<=0.03)
					plevel = "T";
				else if(pratio<=0.015)
					plevel = "U";
				
				ConnDB conn = new ConnDB(Constant.Username, Constant.Password);
				String insert_problem = "insert into problem values('" + pid + "','" + pname + "','" + plevel + "','" + pac + "','" + pratio + "')";
				conn.Insert(insert_problem);
				conn.Close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
