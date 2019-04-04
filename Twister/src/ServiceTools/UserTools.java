package ServiceTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import BaseDD.Database;

public class UserTools {
	public static void InsertUser(String login,String password, String nom,String prenom, String mail) {
		try {
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();

			String query = "INSERT INTO User(user_login,user_password,nom,prenom,mail) VALUES('"+login+"','"+password+"','"+nom+"','"+prenom+"','"+mail+"');";

			int resultat= statement.executeUpdate(query);
		
			statement.close();
			connexion.close();
			System.out.println(resultat);

			}
			catch(SQLException e) {
				
			}
		
		
	}
public static String InsertConnexion( String login) {
		UUID uuid = UUID.randomUUID();
		String key = Long.toString(uuid.getMostSignificantBits(), 36) + Long.toString(uuid.getLeastSignificantBits(), 36);;
		
		try {
			Timestamp fin_session  =new Timestamp(System.currentTimeMillis()+(((3600) + 59)* 1000));
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query ="INSERT INTO Session(session_key,user_login,session_fin) VALUES('"+key+"','"+login+"','"+fin_session+"');";
			int resultat= statement.executeUpdate(query);
			
			statement.close();
			connexion.close();
			System.out.println(resultat);
			
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		
		return key;
	}
public static String getLogin(int id) {
	String log="cc";
	try{
		Connection connexion = Database.getMySQLConnection();
		Statement statement = connexion.createStatement();
		String req = "SELECT user_login FROM User WHERE user_id= '"+id+"'";
		ResultSet res= statement.executeQuery(req);
		
		if(res.next())
			return res.getString(1);
		statement.close();
		connexion.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
	return log;
}
	
}