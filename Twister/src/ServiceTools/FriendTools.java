package ServiceTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;

import BaseDD.Database;

public class FriendTools {
	public static void InsertFriend(String key, int id_friend){

		try{
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();

			String query = "INSERT INTO Friends(id_user1,id_user2) VALUES('"+getId(key)+"','"+id_friend+"');";
			int resultat=statement.executeUpdate(query);
			System.out.println(resultat);
			statement.close();
			connexion.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
	}

	public static int getId(String key){
		String log="";
		try{
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String req = "SELECT user_login FROM Session WHERE session_key= '"+key+"'";
			ResultSet res= statement.executeQuery(req);
			
			if(res.next()){
				log = res.getString(1);
				String query= "SELECT user_id FROM User WHERE user_login= '"+log+"'";
				ResultSet res1=statement.executeQuery(query);
				if(res1.next()){
					return Integer.parseInt(res1.getString(1));
				}
			}else{
				System.out.println("erreur");
			}
		
			statement.close();
			connexion.close();
		}catch(SQLException e){
			
		}
		return 0;
	}
	public static boolean checkFriend(int id_user1,int id_user2){
		try{
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query = "SELECT * FROM Friends WHERE id_user1 = '"+id_user1+"' and id_user2 = '"+id_user2+"' ";
			ResultSet resultat=	statement.executeQuery(query);
			boolean res =resultat.next();
			resultat.close();
			statement.close();
			connexion.close();

			return res;		
		}catch(SQLException e){
			
		}
		return false;
	
	}
	public static boolean checkFriend(int id_friend){
		try{
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query = "SELECT * FROM Friends WHERE id_user2 = '"+id_friend+"'";
			System.out.println(1);
			ResultSet resultat=	statement.executeQuery(query);
			System.out.println(2);
			boolean res =resultat.next();
			resultat.close();
			statement.close();
			connexion.close();

			return res;		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	
	}

}
