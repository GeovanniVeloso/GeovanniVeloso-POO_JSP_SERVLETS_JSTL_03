package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDAOMySQL {

	private Connection c;
	
	public GenericDAOMySQL() {
		super();
	}
	
public Connection getConnection() throws ClassNotFoundException, SQLException{
		
		String hostName = "localhost";
		String dbName = "registrojogadores";
		String user = "root";
		String senha = "Gveloso15";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa?serverTimezone=UTC", user, senha);
		
		return c;
	}

}
