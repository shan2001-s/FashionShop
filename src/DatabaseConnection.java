import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
protected static Connection initiallizeDatabase() {
	Connection con = null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fshop","root","DeveloperShan");
		System.out.println("connected successfully");
			
	}catch(Exception e){
		e.printStackTrace();
	}
	return con;
}
}
