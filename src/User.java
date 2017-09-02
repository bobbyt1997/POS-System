import java.sql.Connection;
import java.sql.Statement;

public class User {
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/pos?useSSL=false";
							  
	//Database credentials
	static final String USER = "root";
	static final String PASS = "42597";
	static Connection conn = null;
	static Statement stmt = null;
	
	String lastName;
	String firstName;
	
	public User(String pLastName, String pFirstName){
		lastName = pLastName;
		firstName = pFirstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public Boolean checkCredentials(String lastName, String firstName, String password){
		return false;
	}
}
