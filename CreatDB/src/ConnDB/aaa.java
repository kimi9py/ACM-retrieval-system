package ConnDB;

import java.io.*;
import java.sql.SQLException;


public class aaa {
	public static void main(String[] args) throws SQLException {
		
		ConnDB conn = new ConnDB(Constant.Username, Constant.Password);
		conn.SearchKidFromKname("≈≈–Ú");

	}
}
