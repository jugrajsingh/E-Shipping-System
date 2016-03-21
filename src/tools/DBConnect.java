package tools;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnect {

	public static Connection dbconnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root", "type");
			//JOptionPane.showMessageDialog(null, "Successfully Connected to Database");
			return conn;
		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			try {
				JOptionPane.showMessageDialog(null, "Database Not Found(May Be DROPPED or INACCESSIBLE)\n\tTrying to Create Again...");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root", "type");
				ScriptRunner runner = new ScriptRunner( conn, false, true);
				runner.runScript(new BufferedReader(new InputStreamReader(ClassLoader.class.getResourceAsStream("/DatabaseFinal.sql"))));
				JOptionPane.showMessageDialog(null, "Database Created...");
				Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root", "type");
				return conn1;
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

	}
}
