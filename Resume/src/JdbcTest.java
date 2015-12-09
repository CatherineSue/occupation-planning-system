import java.sql.*;

/**
 * Class <code>JdbcTest</code> is a class which can connect to a database.
 * For the Project <code>Resume</code>.
 * It imports the data in the resume from standard out to MySQL database. 
 * @author Su Chang  
 * @version 05/13/15 GMT 19:15 
 * @see java.sql.*
 * @since JDK 1.8
 * @Encoding utf-8
 **/
public class JdbcTest{
	String sql = "";
	
	public JdbcTest(String s){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
			String username = "root";
			String password = "suchang123";
			
			// connect to database
			Connection connect = DriverManager.getConnection(url, username, password);
			
			sql = s;
			
			PreparedStatement psql = connect.prepareStatement(sql);
			
			psql.executeUpdate();
			
			//System.out.println("Succeed.");
		} catch (SQLException se) {
			System.out.println("Connect to database failed!");
			se.printStackTrace();
		} catch (ClassNotFoundException e) { 
			System.out.println("Could not fine the Driver Class!");
			e.printStackTrace();
		}
	}

}