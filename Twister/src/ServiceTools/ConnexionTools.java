package ServiceTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


import BaseDD.Database;


public class ConnexionTools {
	/**
	 * verifier que l'utilisateur login existe dans la BD
	 * @param login
	 * @return
	 * @throws SQLException 
	 */
	public static boolean checkUser(String login) throws SQLException {
			Connection connexion = Database.getMySQLConnection();

			Statement statement = connexion.createStatement();
			String query = "SELECT user_login FROM User WHERE user_login ='"+login+"'" ;
			ResultSet resultat=	statement.executeQuery(query);
			
			
			boolean res =resultat.next();
			resultat.close();
			statement.close();
			connexion.close();

			return res;		
	
	}
	/**
	 * verifier que le mot de passe entré correspond au mdp de login
	 * @param login
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public static boolean checkLogPwd(String login, String password) throws SQLException {
			String log="";
				Connection connexion = Database.getMySQLConnection();
				Statement statement = connexion.createStatement();
			
				String query = "SELECT user_password FROM User WHERE user_login = '"+login+"' ";
				ResultSet resultat=	statement.executeQuery(query);
				if(resultat.next())
					log = resultat.getString(1);
			
				resultat.close();
				statement.close();
				connexion.close();		
			return (log.equals(password));
			
	}
	

	
	/**
	 * recuperer le id de l'utilisateur "login"
	 * @param login
	 * @return
	 */
	public static int getId(String login) {
		
		return 0;
	}
	/**
	 * retourner la clef de connexion 
	 * @param login
	 * @param password
	 * @return
	 */
	
	
	/*
	public static boolean checkpassword(String password) {
		String pattern = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,16})";
		return password.matches(pattern);
	}*/
	public static boolean UserConnected(String login) {
		boolean b;
		try {
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			
			String query = "SELECT session_key FROM Session WHERE user_login = '"+login+"' ";
			ResultSet resultat=	statement.executeQuery(query);
			b= resultat.next();
			resultat.close();
			statement.close();
			connexion.close();		
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public static boolean checkSession(String key) {
		
		/**
		 * faire un selsect dans la base de donnees si la session existe
		 */
		
			try{
				Connection connexion = Database.getMySQLConnection();
				Statement statement = connexion.createStatement();
				String query = "SELECT * FROM Session WHERE session_key = '"+key+"' ";
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
	public static boolean checkId(int id_user){
		try{
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query = "SELECT user_login FROM User WHERE user_id = '"+id_user+"' ";
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
	
	public static int updateTimeOut(String key_session) throws SQLException  {

		try {
			Timestamp dateFinSession = new Timestamp(System.currentTimeMillis()+(((3600) + 59)* 1000));

			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query = "UPDATE Session SET session_fin = "+dateFinSession+" WHERE session_key = '"+key_session+"' ";
			int resultat=	statement.executeUpdate(query);
			
			
			return resultat;
		}catch(SQLException s) {
			s.printStackTrace();
		}
		return 0;
		
	}
	public static boolean hasExceededTimeOut(String key_session) throws SQLException {
		Connection connexion = Database.getMySQLConnection();
		
		
		String select = "SELECT session_fin FROM session WHERE key_session = '"+key_session+"'";

		PreparedStatement preparedStatement=null;

		try {
			Statement statement = connexion.createStatement();
			
			ResultSet resultat = statement.executeQuery(select);
			
	        if(resultat.next()){
				Timestamp date_fin=resultat.getTimestamp(1);
				
				return date_fin.before(new Timestamp(System.currentTimeMillis()));

	        }
	        
	        return false;
		}

		finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		
	}
	public static String getKey(int id_user) {
		String log="";
		try{
			log = UserTools.getLogin(id_user);
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String req = "SELECT session_key FROM Session WHERE user_login= '"+log+"'";
			ResultSet res= statement.executeQuery(req);
			
			if(res.next())
				return (res.getString(1));
		
			statement.close();
			connexion.close();
		}catch(SQLException e){
			
		}
		return "erreur";
	}
	
	
	
	
	
}
