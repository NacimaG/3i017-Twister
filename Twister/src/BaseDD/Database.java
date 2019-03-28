package BaseDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;



public class Database {
	private DataSource dataSource; 
	private static Database database=null;
	 
	public Database(String jndiname) throws SQLException{
		
		try {
			dataSource =  (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
				
		}catch (NamingException e) {
			throw new SQLException(jndiname + " is missing in JNDI! : "+e.getMessage());
		}
	}
	public Connection getConnection() throws SQLException {
			return ((Database) dataSource).getConnection();
		
	}
	public static Connection getMySQLConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DBStatic.mysql_pooling==false) {
			return(DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" +DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password));
		}
		else { 
			 
				if (database==null) { 
				database=new Database("jdbc/Djaoudene-Ghout"); 
				} 
				return(database.getConnection()); 
				}
		
		
	
	}
	
	public static MongoDatabase getMongoCollection(){
		MongoClient mongo = MongoClients.create("mongodb://"+DBStatic.mongodb_host);
		return mongo.getDatabase(DBStatic.mongodb_db);
	}
}
	

